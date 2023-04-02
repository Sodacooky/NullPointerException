package soda.npe.serviceauthc.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.common.controller.RestResponse;
import soda.npe.common.utils.JwtAuthUtil;

/**
 * 暴露在外的，用于给用户判断自己是否已经登录、登录是否有效的一个接口
 */
@RestController
@RequestMapping("/check")
public class AuthStateCheckController {

    @Resource
    private JwtAuthUtil jwtAuthUtil;

    @GetMapping("/loginState")
    public RestResponse loginState(@RequestHeader("Authorization") String token) {
        //check is null
        if (token == null) return RestResponse.fail(1, "没有token");
        //check validation
        if (!jwtAuthUtil.validation(token)) return RestResponse.fail(2, "token过期或伪造");
        return RestResponse.ok(null, null);
    }
}
