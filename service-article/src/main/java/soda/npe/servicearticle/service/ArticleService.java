package soda.npe.servicearticle.service;

import cn.hutool.core.date.DateBetween;
import cn.hutool.core.date.DateUnit;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import soda.npe.common.constant.DBConstant;
import soda.npe.common.entity.*;
import soda.npe.common.mapper.*;
import soda.npe.servicearticle.vo.ArticlePreviewVO;
import soda.npe.servicearticle.vo.DoArticlePublishVO;
import soda.npe.servicearticle.vo.ModifyArticleVO;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleService extends ServiceImpl<ArticleMapper, Article> {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private ArticleReplyMapper articleReplyMapper;

    @Resource
    private ApprovalArticleMapper approvalArticleMapper;

    @Resource
    private UserNoticeMapper userNoticeMapper;

    @Resource
    private RedisTemplate<String, ArticlePreviewVO> redisTemplate;

    public List<ArticlePreviewVO> getPublishedBy(Long userId, Integer page) {
        //从数据库获取当前页的数据
        List<Article> found = this.list(new LambdaQueryWrapper<Article>()
                .eq(Article::getPublisherId, userId)
                .orderByDesc(Article::getPublisherId)
                .last("limit " + (DBConstant.PAGE_SIZE * (page - 1)) + "," + DBConstant.PAGE_SIZE));
        return convertToPreviewVO(found);
    }

    public List<ArticlePreviewVO> searchByTime(String keyword, Integer page, Boolean isAsc) {
        List<Article> found = this.list(new LambdaQueryWrapper<Article>()
                .like(Article::getText, keyword.trim())
                .or().like(Article::getCategory, keyword)
                .or().like(Article::getTitle, keyword)
                .orderBy(true, isAsc, Article::getPublishTime)
                .last("limit " + (DBConstant.PAGE_SIZE * (page - 1)) + "," + DBConstant.PAGE_SIZE));
        return convertToPreviewVO(found);
    }


    public List<ArticlePreviewVO> searchByApproval(String keyword, Integer page, Boolean isAsc) {
        //搜索
        List<Article> found = this.getBaseMapper().searchByApproval(keyword.trim(), page, DBConstant.PAGE_SIZE, isAsc);
        return convertToPreviewVO(found);
    }

    public List<ArticlePreviewVO> searchByReplyAmount(String keyword, Integer page, Boolean isAsc) {
        //搜索
        List<Article> found = this.getBaseMapper().searchByReplyAmount(keyword.trim(), page, DBConstant.PAGE_SIZE, isAsc);
        return convertToPreviewVO(found);
    }

    public Long publish(Long userId, DoArticlePublishVO doArticlePublishVO) {
        //construct entity, leave id null
        Article article = new Article();
        Date publishTime = new Date();
        article.setTitle(doArticlePublishVO.getTitle());
        article.setCategory(doArticlePublishVO.getCategory());
        article.setText(doArticlePublishVO.getText());
        article.setPublishTime(publishTime);
        article.setPublisherId(userId);
        //store
        if (!this.save(article)) return null;
        //获取ID
        return this.getOne(new LambdaQueryWrapper<Article>()
                .eq(Article::getPublisherId, userId)
                .orderByDesc(Article::getPublishTime)
                .last("limit 1")).getId();
    }

    private List<ArticlePreviewVO> convertToPreviewVO(List<Article> origin) {
        List<ArticlePreviewVO> result = new ArrayList<>();
        for (var one : origin) {
            ArticlePreviewVO vo = new ArticlePreviewVO();
            //填充公共数据
            vo.setId(one.getId());
            vo.setTitle(one.getTitle());
            vo.setCategory(one.getCategory());
            vo.setPublisherId(one.getPublisherId());
            vo.setPublishTime(one.getPublishTime());
            //填充正文摘要
            if (one.getText().length() > 64) vo.setShortText(one.getText().substring(0, 64) + "...");
            else vo.setShortText(one.getText());
            //填充点赞数量
            Long approvalAmount = approvalArticleMapper.selectCount(
                    new LambdaQueryWrapper<ApprovalArticle>()
                            .eq(ApprovalArticle::getArticleId, one.getId()));
            vo.setApprovalAmount(approvalAmount);
            //填充回复数量
            Long replyAmount = articleReplyMapper.selectCount(
                    new LambdaQueryWrapper<ArticleReply>().eq(ArticleReply::getGoalArticleId, one.getId()));
            vo.setReplyAmount(replyAmount);
            //填充用户昵称和头像URL
            UserInfo foundUserInfo = userInfoMapper.selectById(one.getPublisherId());
            vo.setPublisherNickname(foundUserInfo.getNickname());
            vo.setPublisherAvatar(foundUserInfo.getAvatar());
            //添加到
            result.add(vo);
        }
        return result;
    }

    public List<ArticlePreviewVO> getHomeLatest(Integer page, Date queryTime) {
        //获取范围内的info
        List<Article> infoList = this.list(new LambdaQueryWrapper<Article>()
                .le(Article::getPublishTime, queryTime)
                .orderByDesc(Article::getPublishTime)
                .last("limit " + (DBConstant.PAGE_SIZE * (page - 1)) + "," + DBConstant.PAGE_SIZE));
        //转换成vo
        return convertToPreviewVO(infoList);
    }

    public List<ArticlePreviewVO> getHomeWeekly() {
        //判断redis中有没有，有的话就不再计算了，这是一个非常重量级的计算
        if (Boolean.TRUE.equals(redisTemplate.hasKey("homeWeeklyArticle"))) {
            return redisTemplate.opsForList().range("homeWeeklyArticle", 0, -1);
        }

        /*
          周榜的排序方式，
          获得周内发布的、回复数量排序前100的，
          再以权重 点赞数量*1 + 回答数量*5 - 过去的天数*2 进行计算
         */

        //获取回答数量排序前100的
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        List<Article> replyTop100 = this.getBaseMapper().getWeeklyReplyTop100(calendar.getTime());

        //极端情况，如果没有数据，那么我计算个锤子
        if (replyTop100.isEmpty()) return new ArrayList<>();

        //全部转化为vo
        List<ArticlePreviewVO> voTop100 = convertToPreviewVO(replyTop100);

        //使用TreeMap计算权重并排序，降序
        TreeMap<Long, ArticlePreviewVO> treeMap = new TreeMap<>(Comparator.reverseOrder());
        for (var vo : voTop100) {
            //前面两个数
            Long weight = vo.getApprovalAmount() + vo.getReplyAmount() * 5;
            //天数差
            long daysDelta = DateBetween.create(vo.getPublishTime(), new Date()).between(DateUnit.DAY);
            //最终权重
            weight -= daysDelta * 2;
            //储存
            treeMap.put(weight, vo);
        }

        //取出头部的十个，并储存到redis
        List<ArticlePreviewVO> result = new ArrayList<>();
        Iterator<Long> iterator = treeMap.keySet().iterator();
        while (iterator.hasNext() && result.size() < 10) {
            result.add(treeMap.get(iterator.next()));
        }
        redisTemplate.delete("homeWeeklyArticle");
        redisTemplate.opsForList().leftPushAll("homeWeeklyArticle", result);
        redisTemplate.expire("homeWeeklyArticle", Duration.ofHours(1));

        return result;
    }

    public List<ArticlePreviewVO> getHomeMonthly() {
        //判断redis中有没有，有的话就不再计算了，这是一个非常重量级的计算
        if (Boolean.TRUE.equals(redisTemplate.hasKey("homeMonthlyArticle"))) {
            return redisTemplate.opsForList().range("homeMonthlyArticle", 0, -1);
        }

        /*
          月榜的排序方式，
          获得周内发布的、回复数量排序前200的，
          再以权重 点赞数量*2 + 回答数量*4 - 过去的天数*1 进行计算
         */

        //获取回答数量排序前100的
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        List<Article> replyTop200 = this.getBaseMapper().getMonthlyReplyTop200(calendar.getTime());

        //极端情况，如果没有数据，那么我计算个锤子
        if (replyTop200.isEmpty()) return new ArrayList<>();

        //全部转化为vo
        List<ArticlePreviewVO> voTop200 = convertToPreviewVO(replyTop200);

        //使用TreeMap计算权重并排序，降序
        TreeMap<Long, ArticlePreviewVO> treeMap = new TreeMap<>(Comparator.reverseOrder());
        for (var vo : voTop200) {
            //前面两个数
            Long weight = vo.getApprovalAmount() * 2 + vo.getReplyAmount() * 4;
            //天数差
            long daysDelta = DateBetween.create(vo.getPublishTime(), new Date()).between(DateUnit.DAY);
            //最终权重
            weight -= daysDelta;
            //储存
            treeMap.put(weight, vo);
        }

        //取出头部的十个，并储存到redis
        List<ArticlePreviewVO> result = new ArrayList<>();
        Iterator<Long> iterator = treeMap.keySet().iterator();
        while (iterator.hasNext() && result.size() < 10) {
            result.add(treeMap.get(iterator.next()));
        }
        redisTemplate.delete("homeMonthlyArticle");
        redisTemplate.opsForList().leftPushAll("homeMonthlyArticle", result);
        redisTemplate.expire("homeMonthlyArticle", Duration.ofHours(1));


        return result;
    }

    public List<String> getCategoriesSuggestion(String input) {
        List<Article> match = list(new LambdaQueryWrapper<Article>().likeRight(Article::getCategory, input));
        return match.stream().map(Article::getCategory).distinct().collect(Collectors.toList());
    }

    public boolean adminUpdate(ModifyArticleVO vo) {
        //修改info里面的标题和分类
        Article article = new Article();
        article.setId(vo.getId());
        article.setTitle(vo.getTitle());
        article.setText(vo.getText());
        article.setCategory(vo.getCategory());
        //然后发送一条消息给用户
        UserNotice userNotice = new UserNotice();
        userNotice.setTitle("文章 " + article.getTitle() + " 已被管理员修改");
        userNotice.setText("管理员已将该文章部分内容进行了修改，请确认你已准守社区的规则。");
        userNotice.setGoalUserId(article.getPublisherId());
        userNotice.setTime(new Date());
        userNotice.setType("system");
        userNotice.setIsRead(0);
        userNoticeMapper.insert(userNotice); //不在乎这一条的失败
        return updateById(article);
    }

    public boolean adminRemove(Long articleId) {
        //获取要删除的文章信息，用于构建消息
        Article info = getById(articleId);
        //然后删除文章
        if (!removeById(articleId)) return false;
        //然后发送一条消息给用户
        UserNotice userNotice = new UserNotice();
        userNotice.setTitle("文章 " + info.getTitle() + " 已被管理员删除");
        userNotice.setText("管理员已将该文章连同回复一起删除，请确认你已准守社区的规则。");
        userNotice.setGoalUserId(info.getPublisherId());
        userNotice.setTime(new Date());
        userNotice.setType("system");
        userNotice.setIsRead(0);
        return userNoticeMapper.insert(userNotice) == 1;
        //文章相关回复已被级联删除
    }
}
