package soda.npe.serviceuser.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import soda.npe.common.entity.UserNotice;
import soda.npe.common.mapper.UserNoticeMapper;

import java.util.Date;
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
                .orderByDesc(UserNotice::getTime)
                .last("limit 300"));//限制300条
    }


    public int sendCustomNotice(String[] userIds, String title, String text) {
        int count = 0;
        Date currentDate = new Date();
        for (var userId : userIds) {
            UserNotice userNotice = new UserNotice();
            userNotice.setGoalUserId(Long.parseLong(userId));
            userNotice.setType("system");
            userNotice.setTitle(title);
            userNotice.setText(text);
            userNotice.setTime(currentDate);
            userNotice.setIsRead(0);
            if (this.save(userNotice)) count++;
        }
        return count;
    }

    public boolean read(Long noticeId, Long userId) {
        //先判断消息存不存在
        UserNotice foundNotice = getById(noticeId);
        if (foundNotice == null) return false;
        //然后判断自己是不是消息的主人
        //如果一个用户不存在，那么他一定不是消息的主人，所以不判断用户存在性
        if (foundNotice.getGoalUserId().longValue() != userId) return false;
        //标记已读
        return update(new LambdaUpdateWrapper<UserNotice>()
                .set(UserNotice::getIsRead, 1)
                .eq(UserNotice::getId, noticeId));
    }
}
