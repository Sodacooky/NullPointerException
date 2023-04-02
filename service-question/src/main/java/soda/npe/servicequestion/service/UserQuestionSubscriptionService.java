package soda.npe.servicequestion.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import soda.npe.common.entity.UserInfo;
import soda.npe.common.entity.UserQuestionSubscription;
import soda.npe.common.mapper.UserInfoMapper;
import soda.npe.common.mapper.UserQuestionSubscriptionMapper;

import java.util.Date;

@Service
public class UserQuestionSubscriptionService extends ServiceImpl<UserQuestionSubscriptionMapper, UserQuestionSubscription> {

    @Resource
    private UserInfoMapper userInfoMapper;

    public Boolean subscription(Long questionId, Long userId) {
        //check user valid
        if (!userInfoMapper.exists(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getId, userId))) return false;
        //check already subscribed?
        UserQuestionSubscription subscriptionRecord = this.getOne(new LambdaQueryWrapper<UserQuestionSubscription>()
                .eq(UserQuestionSubscription::getQuestionId, questionId)
                .eq(UserQuestionSubscription::getUserId, userId));
        if (subscriptionRecord != null) return false;
        //do subscription
        UserQuestionSubscription newRecord = new UserQuestionSubscription();
        newRecord.setUserId(userId);
        newRecord.setQuestionId(questionId);
        newRecord.setTime(new Date());
        return this.save(newRecord);
    }

    public Boolean unSubscription(Long questionId, Long userId) {
        //check user valid
        if (!userInfoMapper.exists(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getId, userId))) return false;
        //check already subscribed?
        UserQuestionSubscription subscriptionRecord = this.getOne(new LambdaQueryWrapper<UserQuestionSubscription>()
                .eq(UserQuestionSubscription::getQuestionId, questionId)
                .eq(UserQuestionSubscription::getUserId, userId));
        if (subscriptionRecord == null) return false;
        //do unSubscription, remove record
        return this.remove(new LambdaQueryWrapper<UserQuestionSubscription>()
                .eq(UserQuestionSubscription::getQuestionId, questionId)
                .eq(UserQuestionSubscription::getUserId, userId));
    }

    public Long getSubscriptionAmountOf(Long questionId) {
        return this.count(new LambdaQueryWrapper<UserQuestionSubscription>().eq(UserQuestionSubscription::getQuestionId, questionId));
    }

}
