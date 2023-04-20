package soda.npe.serviceuser.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import soda.npe.common.entity.UserNotice;
import soda.npe.common.mapper.UserNoticeMapper;

import java.util.List;

@Service
public class UserNoticeService extends ServiceImpl<UserNoticeMapper, UserNotice> {
    public Long getNoticeAmount(Long userId) {
        return this.count(new LambdaQueryWrapper<UserNotice>()
                .eq(UserNotice::getGoalUserId, userId)
                .eq(UserNotice::getIsRead, Boolean.FALSE));
    }

    public List<UserNotice> getNotice(Long userId, String typeName) {
        return this.list(new LambdaQueryWrapper<UserNotice>()
                .eq(UserNotice::getGoalUserId, userId)
                .eq(UserNotice::getType, typeName)
                .orderByDesc(UserNotice::getTime));
    }
}
