package soda.npe.servicearticle.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import soda.npe.common.constant.DBConstant;
import soda.npe.common.entity.Article;
import soda.npe.common.entity.ArticleReply;
import soda.npe.common.entity.UserInfo;
import soda.npe.common.entity.UserNotice;
import soda.npe.common.mapper.ArticleMapper;
import soda.npe.common.mapper.ArticleReplyMapper;
import soda.npe.common.mapper.UserInfoMapper;
import soda.npe.common.mapper.UserNoticeMapper;
import soda.npe.servicearticle.vo.ArticleReplyPreviewVO;
import soda.npe.servicearticle.vo.DoArticleReplyVO;
import soda.npe.servicearticle.vo.DoReplyPublishVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleReplyService extends ServiceImpl<ArticleReplyMapper, ArticleReply> {

    @Resource
    private UserNoticeMapper userNoticeMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private ArticleMapper articleMapper;

    public List<DoArticleReplyVO> getOf(Long articleId, Integer page) {
        //取出回复列表
        List<ArticleReply> originReplys = this.list(new LambdaQueryWrapper<ArticleReply>()
                .eq(ArticleReply::getGoalArticleId, articleId)
                .orderByDesc(ArticleReply::getPublishTime)
                .last("limit " + (DBConstant.PAGE_SIZE * (page - 1)) + "," + DBConstant.PAGE_SIZE));
        //填充用户昵称与头像
        List<DoArticleReplyVO> vos = new ArrayList<>(originReplys.size());
        originReplys.forEach(rep -> {
            UserInfo userInfo = userInfoMapper.selectById(rep.getPublisherId());
            DoArticleReplyVO doArticleReplyVO = new DoArticleReplyVO();
            doArticleReplyVO.setId(rep.getId());
            doArticleReplyVO.setPublisherId(rep.getPublisherId());
            doArticleReplyVO.setPublishTime(rep.getPublishTime());
            doArticleReplyVO.setGoalArticleId(rep.getGoalArticleId());
            doArticleReplyVO.setText(rep.getText());
            doArticleReplyVO.setPublisherAvatar(userInfo.getAvatar());
            doArticleReplyVO.setPublisherNickname(userInfo.getNickname());
            vos.add(doArticleReplyVO);
        });
        return vos;
    }


    public Long getReplyAmountOf(Long articleId) {
        return this.count(new LambdaQueryWrapper<ArticleReply>().eq(ArticleReply::getGoalArticleId, articleId));
    }

    public Boolean publish(Long userId, DoReplyPublishVO doReplyPublishVO) {
        //构建reply对象
        ArticleReply articleReply = new ArticleReply();
        articleReply.setPublisherId(userId);
        articleReply.setText(doReplyPublishVO.getText());
        articleReply.setGoalArticleId(doReplyPublishVO.getArticleId());
        articleReply.setPublishTime(new Date());
        //储存
        if (!this.save(articleReply)) return false;
        //构建消息给文章作者
        // - 获取消息发送者信息和文章信息
        UserInfo replyOwner = userInfoMapper.selectById(articleReply.getPublisherId());
        Article article = articleMapper.selectById(articleReply.getGoalArticleId());
        // - 如果回复人就是作者自己，那么跳过
        if (replyOwner.getId().longValue() == article.getPublisherId()) return true;
        // - 填充消息实体
        UserNotice userNotice = new UserNotice();
        userNotice.setTitle("文章 " + article.getTitle() + " 收到来自 " + replyOwner.getNickname() + " 的回复");
        userNotice.setGoalUserId(article.getPublisherId());
        userNotice.setTime(new Date());
        userNotice.setSupplement(articleReply.getGoalArticleId().toString());
        userNotice.setType("article_reply");
        userNotice.setIsRead(0);
        userNotice.setText(articleReply.getText());
        //储存
        return userNoticeMapper.insert(userNotice) == 1;
    }

    public boolean updateReply(Long id, String text) {
        ArticleReply articleReply = new ArticleReply();
        articleReply.setId(id);
        articleReply.setText(text);
        return updateById(articleReply);
    }

    public boolean removeAndNotice(Long replyId) {
        ArticleReply articleReply = getById(replyId);
        Article article = articleMapper.selectById(articleReply.getGoalArticleId());
        //删除
        if (!removeById(replyId)) return false;
        //发送消息
        UserNotice userNotice = new UserNotice();
        userNotice.setTitle("您在问题 " + article.getTitle() + " 下的一条回复已被管理员删除");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("管理员已将该答案该回复，请确认你已准守社区的规则。<br/>");
        stringBuilder.append("您的回复：<br/>");
        stringBuilder.append(articleReply.getText().length() > 64 ? articleReply.getText().substring(0, 64) + "..." : articleReply.getText());
        userNotice.setText(stringBuilder.toString());
        userNotice.setGoalUserId(articleReply.getPublisherId());
        userNotice.setTime(new Date());
        userNotice.setType("system");
        userNotice.setIsRead(0);
        userNotice.setSupplement(article.getId().toString());
        return userNoticeMapper.insert(userNotice) == 1;
    }

    public List<ArticleReplyPreviewVO> searchByTime(String keyword, Integer page, Boolean isAsc) {
        //获得回复
        List<ArticleReply> replies = this.list(new LambdaQueryWrapper<ArticleReply>()
                .like(ArticleReply::getText, keyword.trim())
                .or().like(ArticleReply::getText, keyword)
                .orderBy(true, isAsc, ArticleReply::getPublishTime)
                .last("limit " + (DBConstant.PAGE_SIZE * (page - 1)) + "," + DBConstant.PAGE_SIZE));
        //转换为vo
        return convertToPreviewVO(replies);
    }

    private List<ArticleReplyPreviewVO> convertToPreviewVO(List<ArticleReply> origin) {
        List<ArticleReplyPreviewVO> result = new ArrayList<>();
        for (var one : origin) {
            ArticleReplyPreviewVO vo = new ArticleReplyPreviewVO();
            //填充公共数据
            vo.setId(one.getId());
            vo.setPublisherId(one.getPublisherId());
            vo.setPublishTime(one.getPublishTime());
            vo.setGoalArticleId(one.getGoalArticleId());
            //填充摘要
            if (one.getText().length() > 64) vo.setShortText(one.getText().substring(0, 64) + "...");
            else vo.setShortText(one.getText());
            //填充用户昵称和头像URL
            UserInfo foundUserInfo = userInfoMapper.selectById(one.getPublisherId());
            vo.setPublisherNickname(foundUserInfo.getNickname());
            vo.setPublisherAvatar(foundUserInfo.getAvatar());
            //填充文章信息
            Article article = articleMapper.selectById(vo.getGoalArticleId());
            vo.setGoalArticleTitle(article.getTitle());
            //添加到
            result.add(vo);
        }
        return result;
    }
}
