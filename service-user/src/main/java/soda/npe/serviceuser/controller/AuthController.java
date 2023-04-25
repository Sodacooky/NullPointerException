package soda.npe.serviceuser.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import soda.npe.common.controller.Response;
import soda.npe.serviceuser.service.AuthService;
import soda.npe.serviceuser.vo.LoginVO;
import soda.npe.serviceuser.vo.RegisterVO;

/**
 * 负责用户（当然也有管理员）登录、注册相关的控制器
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private AuthService authService;

    /**
     * 一般用户登录
     *
     * @param loginVO 包含邮箱和密码
     * @return 登录成功返回token，失败返回null
     */
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

    /**
     * 登出
     *
     * @param token jwt
     * @return 返回内部是否成功清除jwt，无论如何一定保证token被清掉了
     */
    @GetMapping("/logout")
    public Response logout(@RequestHeader("Authorization") String token) {
        return Response.ok(authService.doLogout(token));
    }

    /**
     * 判断是否登录
     *
     * @param token 当前jwt
     * @return 是否登录
     */
    @GetMapping("/hasLogin")
    public Response hasLogin(@RequestHeader("Authorization") String token) {
        return Response.ok(authService.hasLogin(token));
    }


    /**
     * 管理员登录
     *
     * @param password 管理员密码
     * @return 是否成功登录
     */
    @PostMapping("/admin/login")
    public Response adminLogin(@RequestBody String password) {
        //validation
        if (StrUtil.isBlank(password)) return Response.fail(1, "密码不能为空");
        //从数据库读取管理员密码，并判断是否可以登录，获取jwt
        String token = authService.adminDoLogin(password);
        if (token == null) return Response.fail(2, "密码错误");
        else return Response.ok(token);
    }

    /**
     * 管理员登出
     *
     * @param token 管理员jwt
     * @return 是否成功清除管理员jwt
     */
    @GetMapping("/admin/logout")
    public Response adminLogout(@RequestHeader("AdminAuthorization") String token) {
        return Response.ok(authService.doLogout(token));//使用user的一样可以
    }

    /**
     * 管理员是否登录检测
     *
     * @param token 管理员jwt
     * @return 是否登录
     */
    @GetMapping("/admin/hasLogin")
    public Response adminHasLogin(@RequestHeader("AdminAuthorization") String token) {
        return Response.ok(authService.hasLogin(token));//使用user的一样可以
    }

    /**
     * 用户注册
     *
     * @param registerVO 注册信息
     * @param session    注入的会话，用于储存验证码
     * @return 是否注册成功
     */
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
        if (!Validator.isEmail(registerVO.getEmail())) {
            return Response.fail(3, "邮箱格式不正确");
        }
        //check code
        LineCaptcha captcha = (LineCaptcha) session.getAttribute("captcha");
        if (captcha == null) return Response.fail(5, "验证码系统错误");
        if (!captcha.verify(registerVO.getCode())) {
            //验证码错误，我们也要重新生成一个
            registerCodeImage(session); //手动强制生成，前端要自行刷新
            return Response.fail(7, "验证码错误");
        }
        //虽然前端已经判断过了两个密码是一样的，这里还是做一下吧
        if (!registerVO.getPassword().equals(registerVO.getConfirmPassword())) {
            return Response.fail(9, "两次密码不匹配");
        }
        //密码长度最小值校验
        if (registerVO.getPassword().length() < 8) return Response.fail(11, "密码太短了");
        //判断邮箱是否已用
        if (authService.isEmailUsed(registerVO.getEmail())) return Response.fail(5, "邮箱已被使用");
        //判断昵称是否已用
        if (authService.isNicknameUsed(registerVO.getNickname())) return Response.fail(6, "昵称已被使用");
        //业务层进行注册
        if (!authService.doRegister(registerVO)) return Response.fail(7, "注册失败，未知错误");
        //注册完成，发送邮件等待用户激活账户
        session.removeAttribute("captcha");
        return Response.ok(null);
    }

    /**
     * 获取验证码图片，主要要先调用/registerCodeImageGenerate
     *
     * @param session 注入的会话
     * @return 图片字节流
     */
    @GetMapping("/registerCodeImage")
    public byte[] registerCodeImage(HttpSession session) {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 90);
        session.setAttribute("captcha", lineCaptcha);
        return lineCaptcha.getImageBytes();
    }


    /**
     * 判断昵称是否可用
     *
     * @param nickname 昵称
     * @return 如果已经被使用则失败
     */
    @GetMapping("/registerNicknameCheck")
    public Response registerNicknameCheck(String nickname) {
        if (authService.isNicknameUsed(nickname)) {
            return Response.fail(1, "昵称已经被使用");
        } else {
            return Response.ok(null);
        }
    }

    /**
     * 判断邮箱是否可用
     *
     * @param email 邮箱
     * @return 如果已经被使用或格式不正确则失败
     */
    @GetMapping("/registerEmailCheck")
    public Response registerEmailCheck(String email) {
        if (!Validator.isEmail(email)) return Response.fail(1, "邮箱格式不正确");
        if (authService.isEmailUsed(email)) return Response.fail(2, "邮箱已经被使用");
        return Response.ok(null);
    }

    /**
     * 进行账号注册激活验证
     *
     * @param magic 注册界面操作成功后会有带有magic的url发送到邮箱，用于区分注册用户
     * @return 是否激活成功
     */
    @GetMapping("/registerVerify")
    public Response registerVerify(String magic) {
        //validation
        if (StrUtil.isBlank(magic)) return Response.fail(1, "缺少参数");
        //do
        if (authService.doRegisterVerify(magic)) return Response.ok(null);
        else return Response.fail(2, "验证失败");
    }


}
