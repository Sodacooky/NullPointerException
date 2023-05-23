package soda.npe.servicequestion.service;

import cn.hutool.core.date.DateBetween;
import cn.hutool.core.date.DateUnit;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import soda.npe.common.constant.DBConstant;
import soda.npe.common.entity.*;
import soda.npe.common.mapper.*;
import soda.npe.servicequestion.vo.ModifyQuestionInfoVO;
import soda.npe.servicequestion.vo.QuestionInfoPreviewVO;
import soda.npe.servicequestion.vo.QuestionPublishVO;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionInfoService extends ServiceImpl<QuestionInfoMapper, QuestionInfo> {

    @Resource
    private QuestionAnswerMapper questionAnswerMapper;

    @Resource
    private UserQuestionSubscriptionMapper userQuestionSubscriptionMapper;

    @Resource
    private UserNoticeMapper userNoticeMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private RedisTemplate<String, QuestionInfoPreviewVO> redisTemplate;

    public Long publish(Long userId, QuestionPublishVO questionPublishVO) {
        //先创建info填写基本信息，id保持为空让mybatis plus自己添加
        Date publishDate = new Date();
        QuestionInfo questionInfo = new QuestionInfo();
        questionInfo.setTitle(questionPublishVO.getTitle());
        questionInfo.setCategory(questionPublishVO.getCategory());
        questionInfo.setPublishTime(publishDate);
        questionInfo.setPublisherId(userId);
        //储存info
        if (!this.save(questionInfo)) return null;
        //取出info的id
        Long infoId = this.getOne(new LambdaQueryWrapper<QuestionInfo>()
                .eq(QuestionInfo::getPublisherId, userId)
                .orderByDesc(QuestionInfo::getPublishTime)
                .last("limit 1")).getId();
        //然后填写answer作为正文
        QuestionAnswer questionAnswer = new QuestionAnswer();
        questionAnswer.setQuestionId(infoId);
        questionAnswer.setPublisherId(userId);
        questionAnswer.setText(questionPublishVO.getText());
        questionAnswer.setOrderNumber(0);
        questionAnswer.setPublishTime(publishDate);
        //储存，失败的话连info也要回滚
        if (questionAnswerMapper.insert(questionAnswer) != 1) {
            //回滚info
            this.removeById(infoId);
            //返回
            return null;
        }
        //自己默认订阅自己的问题
        UserQuestionSubscription userQuestionSubscription = new UserQuestionSubscription();
        userQuestionSubscription.setUserId(questionInfo.getPublisherId());
        userQuestionSubscription.setQuestionId(questionInfo.getId());
        userQuestionSubscription.setTime(publishDate);
        userQuestionSubscriptionMapper.insert(userQuestionSubscription);
        return infoId;
    }


    public String getText(Long questionId) {
        QuestionAnswer found =
                questionAnswerMapper.selectOne(
                        new LambdaQueryWrapper<QuestionAnswer>()
                                .eq(QuestionAnswer::getQuestionId, questionId)
                                .eq(QuestionAnswer::getOrderNumber, 0));
        if (found != null) return found.getText();
        else return null;
    }

    public List<QuestionInfoPreviewVO> searchByTime(String keyword, Integer page, Boolean isAsc) {
        return convertToPreviewVO(getBaseMapper().searchInfoByTime(keyword.trim(), page, DBConstant.PAGE_SIZE, isAsc));
    }

    public List<QuestionInfoPreviewVO> searchBySubscriptionAmount(String keyword, Integer page, Boolean isAsc) {
        return convertToPreviewVO(getBaseMapper().searchInfoBySubscriptionAmount(keyword.trim(), page, DBConstant.PAGE_SIZE, isAsc));
    }

    public List<QuestionInfoPreviewVO> searchByAnswerAmount(String keyword, Integer page, Boolean isAsc) {
        return convertToPreviewVO(getBaseMapper().searchInfoByAnswerAmount(keyword.trim(), page, DBConstant.PAGE_SIZE, isAsc));
    }


    public List<QuestionInfoPreviewVO> getInfoPublishedBy(Long userId, Integer page) {
        return convertToPreviewVO(
                this.list(new LambdaQueryWrapper<QuestionInfo>()
                        .eq(QuestionInfo::getPublisherId, userId)
                        .orderByDesc(QuestionInfo::getPublishTime)
                        .last("limit " + (DBConstant.PAGE_SIZE * (page - 1)) + "," + DBConstant.PAGE_SIZE)));
    }


    //从数据库中获取正文摘要、订阅数量、回答数量填充为VO
    private List<QuestionInfoPreviewVO> convertToPreviewVO(List<QuestionInfo> origin) {
        List<QuestionInfoPreviewVO> result = new ArrayList<>();
        for (var info : origin) {
            QuestionInfoPreviewVO vo = new QuestionInfoPreviewVO();
            //填充公共数据
            vo.setId(info.getId());
            vo.setTitle(info.getTitle());
            vo.setCategory(info.getCategory());
            vo.setPublisherId(info.getPublisherId());
            vo.setPublishTime(info.getPublishTime());
            //填充正文摘要
            String text = getText(info.getId());
            if (text.length() > 64) text = text.substring(0, 64) + "...";
            vo.setShortText(text);
            //填充订阅数量
            Long subscriptionAmount = userQuestionSubscriptionMapper.selectCount(
                    new LambdaQueryWrapper<UserQuestionSubscription>()
                            .eq(UserQuestionSubscription::getQuestionId, info.getId()));
            vo.setSubscriptionAmount(subscriptionAmount);
            //填充回答数量
            Long answerAmount = questionAnswerMapper.selectCount(
                    new LambdaQueryWrapper<QuestionAnswer>()
                            .ge(QuestionAnswer::getOrderNumber, 1)
                            .eq(QuestionAnswer::getQuestionId, info.getId()));
            vo.setAnswerAmount(answerAmount);
            //填充用户昵称和头像URL
            UserInfo foundUserInfo = userInfoMapper.selectById(info.getPublisherId());
            vo.setPublisherNickname(foundUserInfo.getNickname());
            vo.setPublisherAvatar(foundUserInfo.getAvatar());
            //添加到
            result.add(vo);
        }
        return result;
    }


    public List<QuestionInfoPreviewVO> getHomeLatest(Integer page, Date queryTime) {
        //获取范围内的info
        List<QuestionInfo> infoList = this.list(new LambdaQueryWrapper<QuestionInfo>()
                .le(QuestionInfo::getPublishTime, queryTime)
                .orderByDesc(QuestionInfo::getPublishTime)
                .last("limit " + (DBConstant.PAGE_SIZE * (page - 1)) + "," + DBConstant.PAGE_SIZE));
        //转换成vo
        return convertToPreviewVO(infoList);
    }

    public List<QuestionInfoPreviewVO> getHomeWeekly() {
        //判断redis中有没有，有的话就不再计算了，这是一个非常重量级的计算
        if (Boolean.TRUE.equals(redisTemplate.hasKey("homeWeeklyQuestion"))) {
            return redisTemplate.opsForList().range("homeWeeklyQuestion", 0, -1);
        }

        /*
          周榜的排序方式，
          获得周内发布的、回答数量排序前100的，
          再以权重 订阅数量*5 + 回答数量*1 - 过去的天数*2 进行计算
         */

        //获取回答数量排序前100的
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        List<QuestionInfo> answerTop100 = this.getBaseMapper().getWeeklyAnswerTop100(calendar.getTime());

        //极端情况，如果没有数据，那么我计算个锤子
        if (answerTop100.isEmpty()) return new ArrayList<>();

        //全部转化为vo
        List<QuestionInfoPreviewVO> voTop100 = convertToPreviewVO(answerTop100);

        //使用TreeMap计算权重并排序，降序
        TreeMap<Long, QuestionInfoPreviewVO> treeMap = new TreeMap<>(Comparator.reverseOrder());
        for (var vo : voTop100) {
            //前面两个数
            Long weight = vo.getSubscriptionAmount() * 5 + vo.getAnswerAmount();
            //天数差
            long daysDelta = DateBetween.create(vo.getPublishTime(), new Date()).between(DateUnit.DAY);
            //最终权重
            weight -= daysDelta * 2;
            //储存
            treeMap.put(weight, vo);
        }

        //取出头部的十个，并储存到redis
        List<QuestionInfoPreviewVO> result = new ArrayList<>();
        Iterator<Long> iterator = treeMap.keySet().iterator();
        while (iterator.hasNext() && result.size() < 10) {
            result.add(treeMap.get(iterator.next()));
        }
        redisTemplate.delete("homeWeeklyQuestion");
        redisTemplate.opsForList().leftPushAll("homeWeeklyQuestion", result);
        redisTemplate.expire("homeWeeklyQuestion", Duration.ofHours(1));

        return result;
    }

    public List<QuestionInfoPreviewVO> getHomeMonthly() {
        //判断redis中有没有，有的话就不再计算了，这是一个非常重量级的计算
        if (Boolean.TRUE.equals(redisTemplate.hasKey("homeMonthlyQuestion"))) {
            return redisTemplate.opsForList().range("homeMonthlyQuestion", 0, -1);
        }

        /*
          月榜的排序方式，
          获得周内发布的、回答数量排序前200的，
          再以权重 订阅数量*1 + 回答数量*5 - 过去的天数*2 进行计算
         */

        //获取回答数量排序前200的
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        List<QuestionInfo> answerTop200 = this.getBaseMapper().getMonthlyAnswerTop200(calendar.getTime());

        //极端情况，如果没有数据，那么我计算个锤子
        if (answerTop200.isEmpty()) return new ArrayList<>();

        //全部转化为vo
        List<QuestionInfoPreviewVO> voTop200 = convertToPreviewVO(answerTop200);

        //使用TreeMap计算权重并排序，降序
        TreeMap<Long, QuestionInfoPreviewVO> treeMap = new TreeMap<>(Comparator.reverseOrder());
        for (var vo : voTop200) {
            //前面两个数
            Long weight = vo.getSubscriptionAmount() + vo.getAnswerAmount() * 5;
            //天数差
            long daysDelta = DateBetween.create(vo.getPublishTime(), new Date()).between(DateUnit.DAY);
            //最终权重
            weight -= daysDelta * 2;
            //储存
            treeMap.put(weight, vo);
        }

        //取出头部的十个，并储存到redis
        List<QuestionInfoPreviewVO> result = new ArrayList<>();
        Iterator<Long> iterator = treeMap.keySet().iterator();
        while (iterator.hasNext() && result.size() < 10) {
            result.add(treeMap.get(iterator.next()));
        }
        redisTemplate.delete("homeMonthlyQuestion");
        redisTemplate.opsForList().leftPushAll("homeMonthlyQuestion", result);
        redisTemplate.expire("homeMonthlyQuestion", Duration.ofHours(1));

        return result;
    }

    public List<String> getCategoriesSuggestion(String input) {
        List<QuestionInfo> match = list(new LambdaQueryWrapper<QuestionInfo>().likeRight(QuestionInfo::getCategory, input));
        return match.stream().map(QuestionInfo::getCategory).distinct().collect(Collectors.toList());
    }

    public boolean adminUpdate(ModifyQuestionInfoVO vo) {
        //修改info里面的标题和分类
        QuestionInfo info = new QuestionInfo();
        info.setId(vo.getId());
        info.setTitle(vo.getTitle());
        info.setCategory(vo.getCategory());
        if (!updateById(info)) return false;
        //修改问题正文
        LambdaUpdateWrapper<QuestionAnswer> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(QuestionAnswer::getQuestionId, vo.getId())
                .eq(QuestionAnswer::getOrderNumber, 0)
                .set(QuestionAnswer::getText, vo.getText());
        if (questionAnswerMapper.update(null, wrapper) != 1) return false;
        //然后发送一条消息给用户
        UserNotice userNotice = new UserNotice();
        userNotice.setTitle("问题 " + info.getTitle() + " 已被管理员修改");
        userNotice.setText("管理员已将该问题的部分内容进行了修改，请确认你已准守社区的规则。");
        userNotice.setGoalUserId(info.getPublisherId());
        userNotice.setTime(new Date());
        userNotice.setType("system");
        userNotice.setIsRead(0);
        return userNoticeMapper.insert(userNotice) == 1;
    }

    public boolean adminRemove(Long questionId) {
        //获取要删除的问题信息，用于构建消息
        QuestionInfo info = getById(questionId);
        //然后删除问题INFO
        if (!removeById(questionId)) return false;
        //然后发送一条消息给用户
        UserNotice userNotice = new UserNotice();
        userNotice.setTitle("问题 " + info.getTitle() + " 已被管理员删除");
        userNotice.setText("管理员已将该问题连同答案一起删除，请确认你已准守社区的规则。");
        userNotice.setGoalUserId(info.getPublisherId());
        userNotice.setTime(new Date());
        userNotice.setType("system");
        userNotice.setIsRead(0);
        return userNoticeMapper.insert(userNotice) == 1;
        //事务回滚？不存在的！
    }
}
