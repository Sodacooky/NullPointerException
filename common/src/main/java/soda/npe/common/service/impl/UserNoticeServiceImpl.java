package soda.npe.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import soda.npe.common.entity.UserNotice;
import soda.npe.common.service.UserNoticeService;
import soda.npe.common.mapper.UserNoticeMapper;
import org.springframework.stereotype.Service;

/**
* @author sodac
* @description 针对表【user_notice(用户消息)】的数据库操作Service实现
* @createDate 2023-03-28 23:16:19
*/
@Service
public class UserNoticeServiceImpl extends ServiceImpl<UserNoticeMapper, UserNotice>
    implements UserNoticeService{

}




