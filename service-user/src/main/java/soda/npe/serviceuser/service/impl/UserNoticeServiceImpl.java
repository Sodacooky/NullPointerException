package soda.npe.serviceuser.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import soda.npe.serviceuser.entity.UserNotice;
import soda.npe.serviceuser.service.UserNoticeService;
import soda.npe.serviceuser.mapper.UserNoticeMapper;
import org.springframework.stereotype.Service;

/**
* @author sodac
* @description 针对表【user_notice(用户消息)】的数据库操作Service实现
* @createDate 2023-03-05 21:21:28
*/
@Service
public class UserNoticeServiceImpl extends ServiceImpl<UserNoticeMapper, UserNotice>
    implements UserNoticeService{

}




