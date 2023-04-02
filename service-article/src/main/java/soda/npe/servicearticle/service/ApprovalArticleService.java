package soda.npe.servicearticle.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import soda.npe.common.entity.ApprovalArticle;
import soda.npe.common.entity.UserInfo;
import soda.npe.common.mapper.ApprovalArticleMapper;
import soda.npe.common.mapper.UserInfoMapper;

import java.util.Date;

@Service
public class ApprovalArticleService extends ServiceImpl<ApprovalArticleMapper, ApprovalArticle> {

    @Resource
    private UserInfoMapper userInfoMapper;


    public Long getApprovalAmountOf(Long articleId) {
        return this.count(new LambdaQueryWrapper<ApprovalArticle>().eq(ApprovalArticle::getArticleId, articleId));
    }

    public Boolean approve(Long articleId, Long userId) {
        //check user valid
        if (!userInfoMapper.exists(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getId, userId))) return false;
        //check already approved?
        ApprovalArticle approvalRecord = this.getOne(new LambdaQueryWrapper<ApprovalArticle>()
                .eq(ApprovalArticle::getArticleId, articleId)
                .eq(ApprovalArticle::getUserId, userId));
        if (approvalRecord != null) return false;
        //do approval
        ApprovalArticle newRecord = new ApprovalArticle();
        newRecord.setArticleId(articleId);
        newRecord.setUserId(userId);
        newRecord.setTime(new Date());
        return this.save(newRecord);
    }

    public Boolean unApprove(Long articleId, Long userId) {
        //check user valid
        if (!userInfoMapper.exists(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getId, userId))) return false;
        //check already subscribed?
        ApprovalArticle approvalRecord = this.getOne(new LambdaQueryWrapper<ApprovalArticle>()
                .eq(ApprovalArticle::getArticleId, articleId)
                .eq(ApprovalArticle::getUserId, userId));
        if (approvalRecord == null) return false;
        //do unSubscription, remove record
        return this.remove(new LambdaQueryWrapper<ApprovalArticle>()
                .eq(ApprovalArticle::getArticleId, articleId)
                .eq(ApprovalArticle::getUserId, userId));
    }

}
