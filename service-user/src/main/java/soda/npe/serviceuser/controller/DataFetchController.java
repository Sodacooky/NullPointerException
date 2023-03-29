package soda.npe.serviceuser.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.common.controller.RestResponse;
import soda.npe.common.entity.UserInfo;
import soda.npe.serviceuser.service.UserInfoService;

/**
 * 处理用户信息的获取
 */
@RestController
//@RequestMapping("/")
public class DataFetchController {

    @Resource
    private UserInfoService userInfoService;

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @GetMapping("/getUserInfo")
    public RestResponse getUserInfo(Long userId) {
        if (userId == null) return RestResponse.fail(1, "未指定用户");
        UserInfo found = userInfoService.getById(userId);
        if (found == null) return RestResponse.fail(2, "未找到用户");
        return RestResponse.ok(null, found);
    }


}
