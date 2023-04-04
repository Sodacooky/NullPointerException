package soda.npe.serviceuser.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import soda.npe.common.entity.UserNotice;
import soda.npe.common.mapper.UserNoticeMapper;

@Service
public class UserNoticeService extends ServiceImpl<UserNoticeMapper, UserNotice> {
}
