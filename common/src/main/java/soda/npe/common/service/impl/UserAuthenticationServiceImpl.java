package soda.npe.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import soda.npe.common.entity.UserAuthentication;
import soda.npe.common.service.UserAuthenticationService;
import soda.npe.common.mapper.UserAuthenticationMapper;
import org.springframework.stereotype.Service;

/**
* @author sodac
* @description 针对表【user_authentication(用户登录关键信息)】的数据库操作Service实现
* @createDate 2023-03-28 23:16:19
*/
@Service
public class UserAuthenticationServiceImpl extends ServiceImpl<UserAuthenticationMapper, UserAuthentication>
    implements UserAuthenticationService{

}




