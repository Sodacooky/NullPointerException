package soda.npe.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import soda.npe.common.entity.UserInfo;
import soda.npe.common.service.UserInfoService;
import soda.npe.common.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author sodac
* @description 针对表【user_info(用户基本信息表)】的数据库操作Service实现
* @createDate 2023-03-28 23:16:19
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{

}




