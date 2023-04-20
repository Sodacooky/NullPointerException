package soda.npe.serviceuser.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import soda.npe.common.controller.Response;
import soda.npe.serviceuser.service.AuthService;
import soda.npe.serviceuser.vo.LoginVO;
import soda.npe.serviceuser.vo.RegisterVO;

/**
 * 负责用户（当然也有管理员）登录、注册相关的控制器
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private AuthService authService;

    @PostMapping("/login")
    public Response login(@RequestBody LoginVO loginVO) {
        //validation
        if (StrUtil.hasBlank(loginVO.getEmail(), loginVO.getPassword())) {
            return Response.fail(1, "邮箱与密码均不能为空");
        }
        //业务层判断是否可以登录
        if (!authService.tryLogin(loginVO.getEmail(), loginVO.getPassword())) {
            return Response.fail(2, "邮箱或密码错误");
        }
        //业务层生成JWT
        String token = authService.doLogin(loginVO.getEmail(), loginVO.getPassword());
        if (token == null) return Response.fail(3, "账户目前处于冻结状态");
        else return Response.ok(token);
    }

    @GetMapping("/logout")
    public Response logout(@RequestHeader("Authorization") String token) {
        return Response.ok(authService.doLogout(token));
    }


    @PostMapping("/adminLogin")
    public Response adminLogin(@RequestBody String password) {
        //validation
        if (StrUtil.isBlank(password)) return Response.fail(1, "密码不能为空");
        //从数据库读取管理员密码，并判断是否可以登录，获取jwt
        String token = authService.adminDoLogin(password);
        if (token == null) return Response.fail(2, "密码错误");
        else return Response.ok(token);
    }

    @GetMapping("/adminLogout")
    public Response adminLogout(@RequestHeader("AdminAuthorization") String token) {
        return Response.ok(authService.doLogout(token));
    }

    @PostMapping("/register")
    public Response register(@RequestBody RegisterVO registerVO, HttpSession session) {
        //validation
        if (StrUtil.hasBlank(registerVO.getEmail(),
                registerVO.getNickname(),
                registerVO.getPassword(),
                registerVO.getConfirmPassword(),
                registerVO.getCode())) {
            return Response.fail(1, "所有字段均不可为空");
        }
        //check code
        LineCaptcha captcha = (LineCaptcha) session.getAttribute("captcha");
        if (!captcha.verify(registerVO.getCode())) {
            return Response.fail(2, "验证码错误");
        }
        //虽然前端已经判断过了两个密码是一样的，这里还是做一下吧
        if (!registerVO.getPassword().equals(registerVO.getConfirmPassword())) {
            return Response.fail(3, "两次密码不匹配");
        }
        //判断邮箱是否已用
        if (authService.isEmailUsed(registerVO.getEmail())) return Response.fail(4, "邮箱已被使用");
        //判断昵称是否已用
        if (authService.isNicknameUsed(registerVO.getNickname())) return Response.fail(5, "昵称已被使用");
        //业务层进行注册
        if (!authService.doRegister(registerVO)) return Response.fail(6, "注册失败，未知错误");
        //注册完成，发送邮件等待用户激活账户
        return Response.ok(null);
    }

    @GetMapping("/registerVerify")
    public Response registerVerify(String magic) {
        //validation
        if (StrUtil.isBlank(magic)) return Response.fail(1, "缺少参数");
        //do
        if (authService.doRegisterVerify(magic)) return Response.ok(null);
        else return Response.fail(2, "验证失败");
    }

    @GetMapping("/registerCodeImage")
    public byte[] registerCodeImage(HttpSession session) {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        session.setAttribute("captcha", lineCaptcha);
        return lineCaptcha.getImageBytes();
    }


}
