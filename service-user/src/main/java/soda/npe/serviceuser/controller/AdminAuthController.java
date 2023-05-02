package soda.npe.serviceuser.controller;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import soda.npe.common.controller.Response;
import soda.npe.serviceuser.service.AuthService;
import soda.npe.serviceuser.vo.AdminLoginVO;

@RestController
@RequestMapping("/admin")
public class AdminAuthController {
    @Resource
    private AuthService authService;

    /**
     * 管理员登录
     *
     * @param vo 管理员密码
     * @return 是否成功登录
     */
    @PostMapping("/login")
    public Response adminLogin(@RequestBody AdminLoginVO vo) {
        //validation
        if (StrUtil.isBlank(vo.getPassword())) return Response.fail(1, "密码不能为空");
        //从数据库读取管理员密码，并判断是否可以登录，获取jwt
        String token = authService.adminDoLogin(vo.getPassword());
        if (token == null) return Response.fail(2, "密码错误");
        else return Response.ok(token);
    }

    /**
     * 管理员登出
     *
     * @param token 管理员jwt
     * @return 是否成功清除管理员jwt
     */
    @GetMapping("/logout")
    public Response adminLogout(@RequestHeader("AdminAuthorization") String token) {
        return Response.ok(authService.doLogout(token));//使用user的一样可以
    }

    /**
     * 管理员是否登录检测
     *
     * @param token 管理员jwt
     * @return 是否登录
     */
    @GetMapping("/hasLogin")
    public Response adminHasLogin(@RequestHeader("AdminAuthorization") String token) {
        return Response.ok(authService.hasLogin(token));//使用user的一样可以
    }
}
