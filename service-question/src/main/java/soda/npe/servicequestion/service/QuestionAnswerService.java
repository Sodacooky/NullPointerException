package soda.npe.servicequestion.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import soda.npe.common.constant.DBConstant;
import soda.npe.common.entity.QuestionAnswer;
import soda.npe.common.entity.QuestionInfo;
import soda.npe.common.entity.UserInfo;
import soda.npe.common.entity.UserNotice;
import soda.npe.common.mapper.QuestionAnswerMapper;
import soda.npe.common.mapper.QuestionInfoMapper;
import soda.npe.common.mapper.UserInfoMapper;
import soda.npe.common.mapper.UserNoticeMapper;
import soda.npe.servicequestion.vo.AnswerPublishVO;

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

    public List<QuestionAnswer> getAnswerPublishedBy(long userId, int page) {
        return this.list(
                new LambdaQueryWrapper<QuestionAnswer>()
                        .eq(QuestionAnswer::getPublisherId, userId)
                        .orderByDesc(QuestionAnswer::getPublishTime)
                        .last("limit " + (DBConstant.PAGE_SIZE * (page - 1)) + "," + DBConstant.PAGE_SIZE));
    }

    public List<QuestionAnswer> getByTimeOf(Long questionId, Integer page, Boolean isAsc) {
        return this.list(
                new LambdaQueryWrapper<QuestionAnswer>()
                        .eq(QuestionAnswer::getQuestionId, questionId)
                        .ge(QuestionAnswer::getOrderNumber, 1)
                        .orderBy(true, isAsc, QuestionAnswer::getPublishTime)
                        .last("limit " + (DBConstant.PAGE_SIZE * (page - 1)) + "," + DBConstant.PAGE_SIZE));
    }

    public List<QuestionAnswer> getByApprovalOf(Long questionId, Integer page, Boolean isAsc) {
        return this.getBaseMapper().getOfQuestionByApproval(questionId, page, DBConstant.PAGE_SIZE, isAsc);
    }

    public List<QuestionAnswer> searchByTime(String keyword, Integer page, Boolean isAsc) {
        return this.list(
                new LambdaQueryWrapper<QuestionAnswer>()
                        .like(QuestionAnswer::getText, keyword.trim())
                        .orderByDesc(QuestionAnswer::getPublishTime)
                        .last("limit " + (DBConstant.PAGE_SIZE * (page - 1)) + "," + DBConstant.PAGE_SIZE));
    }

    public List<QuestionAnswer> searchByApproval(String keyword, Integer page, Boolean isAsc) {
        return this.getBaseMapper().searchAnswerByApproval(keyword.trim(), page, isAsc);
    }

    public Long getAnswerAmountOf(Long questionId) {
        return this.count(new LambdaQueryWrapper<QuestionAnswer>().eq(QuestionAnswer::getQuestionId, questionId));
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
        //构建消息
        // - 获取用户和问题信息
        UserInfo replyOwner = userInfoMapper.selectById(questionAnswer.getPublisherId());
        QuestionInfo questionInfo = questionInfoMapper.selectById(questionAnswer.getQuestionId());
        // - 填充实体
        UserNotice userNotice = new UserNotice();
        userNotice.setTitle("问题 " + questionInfo.getTitle() + " 收到来自 " + replyOwner.getNickname() + " 的回复");
        userNotice.setText(questionAnswer.getText());
        userNotice.setGoalUserId(questionInfo.getPublisherId());
        userNotice.setTime(new Date());
        userNotice.setType("question_answer");
        userNotice.setIsRead(0);
        userNotice.setSupplement(questionInfo.getId().toString());
        //储存
        return userNoticeMapper.insert(userNotice) == 1;
    }
}
