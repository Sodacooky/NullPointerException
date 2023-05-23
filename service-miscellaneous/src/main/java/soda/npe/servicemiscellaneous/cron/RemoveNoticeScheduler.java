package soda.npe.servicemiscellaneous.cron;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import soda.npe.common.entity.UserNotice;
import soda.npe.common.mapper.UserNoticeMapper;

import java.util.Date;

@Component
@Slf4j
public class RemoveNoticeScheduler {

    @Resource
    private UserNoticeMapper userNoticeMapper;

    //every day
    @Scheduled(cron = "0 0 5 * * ?")
    public void removeOutdatedHasRead() {
        LambdaQueryWrapper<UserNotice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserNotice::getIsRead, 1)
                .lt(UserNotice::getTime, DateUtil.offsetDay(new Date(), -30));
        int amount = userNoticeMapper.delete(wrapper);
        log.warn("移除了 {} 条已读且超过30天的用户消息", amount);
    }

    //every monday
    @Scheduled(cron = "0 0 4 ? * MON")
    public void removeOutdatedUnread() {
        LambdaQueryWrapper<UserNotice> wrapper = new LambdaQueryWrapper<>();
        wrapper.lt(UserNotice::getTime, DateUtil.offsetDay(new Date(), -180));
        int amount = userNoticeMapper.delete(wrapper);
        log.warn("移除了 {} 条超过180天的用户消息", amount);
    }

}
