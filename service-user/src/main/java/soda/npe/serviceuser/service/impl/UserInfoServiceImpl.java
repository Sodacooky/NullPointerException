package soda.npe.serviceuser.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import soda.npe.serviceuser.entity.UserInfo;
import soda.npe.serviceuser.service.UserInfoService;
import soda.npe.serviceuser.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author sodac
* @description 针对表【user_info(用户基本信息表)】的数据库操作Service实现
* @createDate 2023-03-05 21:21:28
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{

}




