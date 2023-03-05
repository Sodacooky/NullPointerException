package soda.npe.serviceuser.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.serviceuser.entity.UserInfo;
import soda.npe.serviceuser.service.UserInfoService;

import java.util.List;

@RestController
public class TestController {

    @Resource
    private UserInfoService userInfoService;

    @GetMapping("/all")
    public List<UserInfo> listAll() {
        return userInfoService.list();
    }

}
