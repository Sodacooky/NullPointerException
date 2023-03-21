package soda.npe.serviceuser.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.servicecommon.entity.UserInfo;
import soda.npe.servicecommon.mapper.UserInfoMapper;

import java.util.List;


@RestController
public class TestController {

    @Resource
    private UserInfoMapper userInfoMapper;

    @GetMapping("/all")
    public List<UserInfo> listAll() {
        return userInfoMapper.selectList(null);
    }

}
