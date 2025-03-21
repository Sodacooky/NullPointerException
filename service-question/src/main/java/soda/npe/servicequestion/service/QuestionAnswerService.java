package soda.npe.servicequestion.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import soda.npe.common.constant.DBConstant;
import soda.npe.common.entity.*;
import soda.npe.common.mapper.*;
import soda.npe.servicequestion.vo.AnswerPublishVO;
import soda.npe.servicequestion.vo.QuestionAnswerPreviewVO;
import soda.npe.servicequestion.vo.QuestionAnswerReadingVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class QuestionAnswerService extends ServiceImpl<QuestionAnswerMapper, QuestionAnswer> {

    @Resource
    private UserNoticeMapper userNoticeMapper;

    @Resource
    private QuestionInfoMapper questionInfoMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private ApprovalAnswerMapper approvalAnswerMapper;

    @Resource
    private UserQuestionSubscriptionMapper userQuestionSubscriptionMapper;

    public List<QuestionAnswerPreviewVO> getAnswerPublishedBy(long userId, int page) {
        List<QuestionAnswer> qa = this.list(
                new LambdaQueryWrapper<QuestionAnswer>()
                        .eq(QuestionAnswer::getPublisherId, userId)
                        .gt(QuestionAnswer::getOrderNumber, 0)
                        .orderByDesc(QuestionAnswer::getPublishTime)
                        .last("limit " + (DBConstant.PAGE_SIZE * (page - 1)) + "," + DBConstant.PAGE_SIZE));
        return convertToPreviewVO(qa);
    }

    public List<QuestionAnswerReadingVO> getByTimeOf(Long questionId, Integer page, Boolean isAsc) {
        List<QuestionAnswer> qa = this.list(
                new LambdaQueryWrapper<QuestionAnswer>()
                        .eq(QuestionAnswer::getQuestionId, questionId)
                        .gt(QuestionAnswer::getOrderNumber, 0)
                        .orderBy(true, isAsc, QuestionAnswer::getPublishTime)
                        .last("limit " + (DBConstant.PAGE_SIZE * (page - 1)) + "," + DBConstant.PAGE_SIZE));
        return convertToReadingVO(qa);
    }

    public List<QuestionAnswerReadingVO> getByApprovalOf(Long questionId, Integer page, Boolean isAsc) {
        List<QuestionAnswer> qa = this.getBaseMapper().getOfQuestionByApproval(questionId, page, DBConstant.PAGE_SIZE, isAsc);
        return convertToReadingVO(qa);
    }

    public List<QuestionAnswerPreviewVO> searchByTime(String keyword, Integer page, Boolean isAsc) {
        List<QuestionAnswer> answers = this.list(
                new LambdaQueryWrapper<QuestionAnswer>()
                        .like(QuestionAnswer::getText, keyword.trim())
                        .gt(QuestionAnswer::getOrderNumber, 0)
                        .orderBy(true, isAsc, QuestionAnswer::getPublishTime)
                        .last("limit " + (DBConstant.PAGE_SIZE * (page - 1)) + "," + DBConstant.PAGE_SIZE));
        return convertToPreviewVO(answers);
    }

    public List<QuestionAnswerPreviewVO> searchByApproval(String keyword, Integer page, Boolean isAsc) {
        return convertToPreviewVO(this.getBaseMapper().searchAnswerByApproval(keyword.trim(), page, isAsc));
    }

    public Long getAnswerAmountOf(Long questionId) {
        return this.count(new LambdaQueryWrapper<QuestionAnswer>()
                .eq(QuestionAnswer::getQuestionId, questionId)
                .ge(QuestionAnswer::getOrderNumber, 1));
    }

    public Boolean publish(Long userId, AnswerPublishVO answerPublishVO) {
        //获取当前问题的楼层标号
        Integer currentOrderNumber = this.getOne(new LambdaQueryWrapper<QuestionAnswer>()
                .eq(QuestionAnswer::getQuestionId, answerPublishVO.getQuestionId())
                .orderByDesc(QuestionAnswer::getOrderNumber)
                .last("limit 1")).getOrderNumber();
        //构建answer实体
        QuestionAnswer questionAnswer = new QuestionAnswer();
        questionAnswer.setQuestionId(answerPublishVO.getQuestionId());
        questionAnswer.setOrderNumber(currentOrderNumber + 1);
        questionAnswer.setPublisherId(userId);
        questionAnswer.setText(answerPublishVO.getText());
        questionAnswer.setPublishTime(new Date());
        //储存
        if (!this.save(questionAnswer)) return false;
        //异步发送通知
        ThreadUtil.execAsync(() -> sendSubscriptionNotice(questionAnswer));
        return true;
    }

    private void sendSubscriptionNotice(QuestionAnswer answer) {
        //构建消息
        // - 获取用户和问题信息
        UserInfo replyOwner = userInfoMapper.selectById(answer.getPublisherId());
        QuestionInfo questionInfo = questionInfoMapper.selectById(answer.getQuestionId());
        // - 填充实体
        UserNotice userNotice = new UserNotice();
        userNotice.setTitle("问题 " + questionInfo.getTitle() + " 收到来自 " + replyOwner.getNickname() + " 的回答");
        userNotice.setText(answer.getText());
        userNotice.setGoalUserId(questionInfo.getPublisherId());
        userNotice.setTime(new Date());
        userNotice.setType("question_answer");
        userNotice.setIsRead(0);
        userNotice.setSupplement(questionInfo.getId().toString());
        //对订阅了问题的用户发送消息
        // - 构建条件：对指定问题60天内订阅的用户
        LambdaQueryWrapper<UserQuestionSubscription> wrapper = new LambdaQueryWrapper<UserQuestionSubscription>()
                .eq(UserQuestionSubscription::getQuestionId, questionInfo.getId())
                .gt(UserQuestionSubscription::getTime, DateUtil.offsetDay(new Date(), -60).toJdkDate());
        // - 获取，高内存占用风险！
        List<UserQuestionSubscription> subscriptions = userQuestionSubscriptionMapper.selectList(wrapper);
        // - 对其中每一个用户发送消息
        for (var sub : subscriptions) {
            userNotice.setGoalUserId(sub.getUserId());
            userNoticeMapper.insert(userNotice);
        }
    }

    private List<QuestionAnswerReadingVO> convertToReadingVO(List<QuestionAnswer> origin) {
        List<QuestionAnswerReadingVO> result = new ArrayList<>();
        for (var one : origin) {
            QuestionAnswerReadingVO vo = new QuestionAnswerReadingVO();
            //填充公共数据
            vo.setId(one.getId());
            vo.setText(one.getText());
            vo.setOrderNumber(one.getOrderNumber());
            vo.setPublisherId(one.getPublisherId());
            vo.setPublishTime(one.getPublishTime());
            vo.setQuestionId(one.getQuestionId());
            //填充点赞数量
            Long approvalAmount = approvalAnswerMapper.selectCount(
                    new LambdaQueryWrapper<ApprovalAnswer>().eq(ApprovalAnswer::getAnswerId, one.getId()));
            vo.setApprovalAmount(approvalAmount);
            //填充用户昵称和头像URL
            UserInfo foundUserInfo = userInfoMapper.selectById(one.getPublisherId());
            vo.setPublisherNickname(foundUserInfo.getNickname());
            vo.setPublisherAvatar(foundUserInfo.getAvatar());
            //添加到
            result.add(vo);
        }
        return result;
    }

    private List<QuestionAnswerPreviewVO> convertToPreviewVO(List<QuestionAnswer> origin) {
        List<QuestionAnswerPreviewVO> result = new ArrayList<>();
        for (var one : origin) {
            QuestionAnswerPreviewVO vo = new QuestionAnswerPreviewVO();
            //填充公共数据
            vo.setId(one.getId());
            vo.setShortText(one.getText().length() > 64 ? one.getText().substring(0, 64) + "..." : one.getText());
            vo.setPublisherId(one.getPublisherId());
            vo.setPublishTime(one.getPublishTime());
            vo.setQuestionId(one.getQuestionId());
            //填充点赞数量
            Long approvalAmount = approvalAnswerMapper.selectCount(
                    new LambdaQueryWrapper<ApprovalAnswer>().eq(ApprovalAnswer::getAnswerId, one.getId()));
            vo.setApprovalAmount(approvalAmount);
            //填充用户昵称和头像URL
            UserInfo foundUserInfo = userInfoMapper.selectById(one.getPublisherId());
            vo.setPublisherNickname(foundUserInfo.getNickname());
            vo.setPublisherAvatar(foundUserInfo.getAvatar());
            //填充问题数据
            vo.setQuestionTitle(questionInfoMapper.selectById(one.getQuestionId()).getTitle());
            //添加到
            result.add(vo);
        }
        return result;
    }

    public boolean adminUpdate(Long answerId, String newText) {
        QuestionAnswer questionAnswer = new QuestionAnswer();
        questionAnswer.setId(answerId);
        questionAnswer.setText(newText);
        if (!updateById(questionAnswer)) return false;
        //然后发送一条消息给用户
        QuestionInfo questionInfo = questionInfoMapper.selectById(questionAnswer.getQuestionId());
        UserNotice userNotice = new UserNotice();
        userNotice.setTitle("您在问题 " + questionInfo.getTitle() + " 下的一条回答已被管理员修改");
        userNotice.setText("管理员已将该回答的部分内容进行了修改，请确认你已准守社区的规则。");
        userNotice.setGoalUserId(questionAnswer.getPublisherId());
        userNotice.setTime(new Date());
        userNotice.setType("system");
        userNotice.setIsRead(0);
        return userNoticeMapper.insert(userNotice) == 1;
    }

    public boolean adminRemove(Long answerId) {
        QuestionAnswer answer = getById(answerId);
        QuestionInfo info = questionInfoMapper.selectById(answer.getQuestionId());
        //删除
        if (!removeById(answerId)) return false;
        //发送消息
        UserNotice userNotice = new UserNotice();
        userNotice.setTitle("您在问题 " + info.getTitle() + " 下的一条回答已被管理员删除");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("管理员已将该答案删除，请确认你已准守社区的规则。<br/>");
        stringBuilder.append("您的回答：<br/>");
        stringBuilder.append(answer.getText().length() > 64 ? answer.getText().substring(0, 64) + "..." : answer.getText());
        userNotice.setText(stringBuilder.toString());
        userNotice.setGoalUserId(answer.getPublisherId());
        userNotice.setTime(new Date());
        userNotice.setType("system");
        userNotice.setIsRead(0);
        userNotice.setSupplement(info.getId().toString());
        return userNoticeMapper.insert(userNotice) == 1;
    }
}
