package soda.npe.servicequestion.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import soda.npe.common.entity.QuestionAnswer;
import soda.npe.common.entity.QuestionInfo;
import soda.npe.common.entity.UserQuestionSubscription;
import soda.npe.common.mapper.QuestionAnswerMapper;
import soda.npe.common.mapper.QuestionInfoMapper;
import soda.npe.common.mapper.UserQuestionSubscriptionMapper;

@Service
public class QuestionInfoService extends ServiceImpl<QuestionInfoMapper, QuestionInfo> {

    @Resource
    private QuestionAnswerMapper questionAnswerMapper;

    @Resource
    private UserQuestionSubscriptionMapper userQuestionSubscriptionMapper;

    public String getText(long questionId) {
        QuestionAnswer found =
                questionAnswerMapper.selectOne(
                        new LambdaQueryWrapper<QuestionAnswer>()
                                .eq(QuestionAnswer::getQuestionId, questionId)
                                .eq(QuestionAnswer::getOrderNumber, 0));
        if (found != null) return found.getText();
        else return null;
    }

    public long getAnswerAmount(long questionId) {
        return questionAnswerMapper.selectCount(
                new LambdaQueryWrapper<QuestionAnswer>()
                        .eq(QuestionAnswer::getQuestionId, questionId));
    }

    public long getSubscriptionAmount(long questionId) {
        return userQuestionSubscriptionMapper.selectCount(
                new LambdaQueryWrapper<UserQuestionSubscription>()
                        .eq(UserQuestionSubscription::getQuestionId, questionId));
    }

}
