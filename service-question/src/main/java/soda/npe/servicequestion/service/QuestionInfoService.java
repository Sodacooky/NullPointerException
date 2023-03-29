package soda.npe.servicequestion.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import soda.npe.common.constant.DBConstant;
import soda.npe.common.entity.QuestionAnswer;
import soda.npe.common.entity.QuestionInfo;
import soda.npe.common.entity.UserQuestionSubscription;
import soda.npe.common.mapper.QuestionAnswerMapper;
import soda.npe.common.mapper.QuestionInfoMapper;
import soda.npe.common.mapper.UserQuestionSubscriptionMapper;

import java.util.List;

@Service
public class QuestionInfoService extends ServiceImpl<QuestionInfoMapper, QuestionInfo> {

    @Resource
    private QuestionAnswerMapper questionAnswerMapper;

    @Resource
    private UserQuestionSubscriptionMapper userQuestionSubscriptionMapper;


    public String getText(Long questionId) {
        QuestionAnswer found =
                questionAnswerMapper.selectOne(
                        new LambdaQueryWrapper<QuestionAnswer>()
                                .eq(QuestionAnswer::getQuestionId, questionId)
                                .eq(QuestionAnswer::getOrderNumber, 0));
        if (found != null) return found.getText();
        else return null;
    }

    public long getAnswerAmount(Long questionId) {
        return questionAnswerMapper.selectCount(
                new LambdaQueryWrapper<QuestionAnswer>()
                        .eq(QuestionAnswer::getQuestionId, questionId));
    }

    public long getSubscriptionAmount(Long questionId) {
        return userQuestionSubscriptionMapper.selectCount(
                new LambdaQueryWrapper<UserQuestionSubscription>()
                        .eq(UserQuestionSubscription::getQuestionId, questionId));
    }

    public List<QuestionInfo> searchInfoByTime(String keyword, Integer page, Boolean isAsc) {
        return getBaseMapper().searchInfoByTime(keyword, page, DBConstant.PAGE_SIZE, isAsc);
    }

    public List<QuestionInfo> searchInfoBySubscriptionAmount(String keyword, Integer page, Boolean isAsc) {
        return getBaseMapper().searchInfoBySubscriptionAmount(keyword, page, DBConstant.PAGE_SIZE, isAsc);
    }


    public List<QuestionInfo> getInfoPublishedBy(Long userId, Integer page) {
        return this.list(
                new LambdaQueryWrapper<QuestionInfo>()
                        .eq(QuestionInfo::getPublisherId, userId)
                        .orderByDesc(QuestionInfo::getPublishTime)
                        .last("limit " + (DBConstant.PAGE_SIZE * (page - 1)) + "," + DBConstant.PAGE_SIZE));
    }

}
