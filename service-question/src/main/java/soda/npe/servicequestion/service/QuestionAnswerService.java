package soda.npe.servicequestion.service;

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

    public List<QuestionAnswer> searchByTime(String keyword, Integer page, Boolean isAsc) {
        return this.list(
                new LambdaQueryWrapper<QuestionAnswer>()
                        .like(QuestionAnswer::getText, keyword.trim())
                        .orderBy(true, isAsc, QuestionAnswer::getPublishTime)
                        .last("limit " + (DBConstant.PAGE_SIZE * (page - 1)) + "," + DBConstant.PAGE_SIZE));
    }

    public List<QuestionAnswer> searchByApproval(String keyword, Integer page, Boolean isAsc) {
        return this.getBaseMapper().searchAnswerByApproval(keyword.trim(), page, isAsc);
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
        //构建消息
        // - 获取用户和问题信息
        UserInfo replyOwner = userInfoMapper.selectById(questionAnswer.getPublisherId());
        QuestionInfo questionInfo = questionInfoMapper.selectById(questionAnswer.getQuestionId());
        // - 自己回答自己不通知
        if (questionInfo.getPublisherId().longValue() == replyOwner.getId()) return true;
        // - 填充实体
        UserNotice userNotice = new UserNotice();
        userNotice.setTitle("问题 " + questionInfo.getTitle() + " 收到来自 " + replyOwner.getNickname() + " 的回答");
        userNotice.setText(questionAnswer.getText());
        userNotice.setGoalUserId(questionInfo.getPublisherId());
        userNotice.setTime(new Date());
        userNotice.setType("question_answer");
        userNotice.setIsRead(0);
        userNotice.setSupplement(questionInfo.getId().toString());
        //储存
        return userNoticeMapper.insert(userNotice) == 1;
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
}
