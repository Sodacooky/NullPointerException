package soda.npe.serviceuser.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import soda.npe.common.constant.DBConstant;
import soda.npe.common.entity.UserInfo;
import soda.npe.common.mapper.UserInfoMapper;

import java.util.List;

@Service
public class UserInfoService extends ServiceImpl<UserInfoMapper, UserInfo> {

    public List<UserInfo> searchByRegisterTime(String keyword, Integer page, Boolean isAsc) {
        return this.list(
                new LambdaQueryWrapper<UserInfo>()
                        .like(UserInfo::getNickname, keyword)
                        .or().like(UserInfo::getId, keyword)
                        .or().like(UserInfo::getDescription, keyword)
                        .orderBy(true, isAsc, UserInfo::getRegisterTime)
                        .last("limit " + (DBConstant.PAGE_SIZE * (page - 1)) + "," + DBConstant.PAGE_SIZE));
    }

}
