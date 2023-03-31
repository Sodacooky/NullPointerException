package soda.npe.servicequestion.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import soda.npe.common.entity.ApprovalAnswer;
import soda.npe.common.entity.UserInfo;
import soda.npe.common.entity.UserQuestionSubscription;
import soda.npe.common.mapper.ApprovalAnswerMapper;
import soda.npe.common.mapper.UserInfoMapper;

import java.util.Date;

@Service
public class ApprovalAnswerService extends ServiceImpl<ApprovalAnswerMapper, ApprovalAnswer> {

    @Resource
    private UserInfoMapper userInfoMapper;

    public Boolean approve(Long answerId,Long userId){
        //check user valid
        if (!userInfoMapper.exists(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getId, userId))) return false;
        //check already approved?
        ApprovalAnswer approvalRecord = this.getOne(new LambdaQueryWrapper<ApprovalAnswer>()
                .eq(ApprovalAnswer::getAnswerId, answerId)
                .eq(ApprovalAnswer::getUserId, userId));
        if (approvalRecord != null) return false;
        //do approval
        ApprovalAnswer newRecord = new ApprovalAnswer();
        newRecord.setAnswerId(answerId);
        newRecord.setUserId(userId);
        newRecord.setTime(new Date());
        return this.save(newRecord);
    }

    public Boolean unApprove(Long answerId,Long userId){
        //check user valid
        if (!userInfoMapper.exists(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getId, userId))) return false;
        //check already subscribed?
        ApprovalAnswer approvalRecord = this.getOne(new LambdaQueryWrapper<ApprovalAnswer>()
                .eq(ApprovalAnswer::getAnswerId, answerId )
                .eq(ApprovalAnswer::getUserId, userId));
        if (approvalRecord == null) return false;
        //do unSubscription, remove record
        return this.remove(new LambdaQueryWrapper<ApprovalAnswer>()
                .eq(ApprovalAnswer::getAnswerId, answerId)
                .eq(ApprovalAnswer::getUserId, userId));
    }

}
