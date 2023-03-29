package soda.npe.serviceuser.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.common.controller.RestResponse;
import soda.npe.serviceuser.service.UserInfoService;

/**
 * 搜索相关
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    @Resource
    private UserInfoService userInfoService;

    /**
     * 根据注册时间顺序，从用户昵称、ID、自我描述中搜索用户
     *
     * @param keyword 关键词
     * @param page    页
     * @param isAsc   是否时间顺序（从老到新）
     * @return 符合要求的用户实体列表
     */
    @GetMapping("/searchInfoByRegisterTime")
    public RestResponse searchInfoByRegisterTime(String keyword, Integer page, Boolean isAsc) {
        if (page == null || page < 1) page = 1;
        if (isAsc == null) isAsc = false;
        return RestResponse.ok(null, userInfoService.searchByRegisterTime(keyword, page, isAsc));
    }

}
