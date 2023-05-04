package soda.npe.serviceuser.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import soda.npe.common.controller.Response;
import soda.npe.common.entity.UserInfo;
import soda.npe.common.utils.JwtAuthUtil;
import soda.npe.serviceuser.service.AuthService;
import soda.npe.serviceuser.service.UserInfoService;
import soda.npe.serviceuser.vo.UpdateInfoVO;
import soda.npe.serviceuser.vo.UpdatePasswordVO;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/auth")
public class OperationController {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private AuthService authService;

    @Resource
    private JwtAuthUtil jwtAuthUtil;

    /**
     * 获取当前用户的信息
     *
     * @param token header中的jwt
     * @return UserInfo实体
     */
    @GetMapping("/getCurrentUser")
    public Response getCurrentUser(@RequestHeader("Authorization") String token) {
        //到达该接口默认token已经经过了验证
        //从jwt中提取userId
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //获取userinfo
        UserInfo found = userInfoService.getById(userId);
        //返回
        return Response.ok(null, found);
    }

    //上传头像
    @PostMapping("/uploadAvatar")
    public Response uploadAvatar(MultipartFile file, @RequestHeader("Authorization") String token) {
        //获取userId
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //判断文件大小
        if (file.getSize() > 2 * 1024 * 1024) {
            //>2MB
            return Response.fail(1, "图片超过2MB");
        }
        //转换成缓冲流，并判断文件类型
        BufferedInputStream bufferedInputStream;
        String type;
        try {
            bufferedInputStream = new BufferedInputStream(file.getInputStream());
            type = FileTypeUtil.getType(bufferedInputStream);
            // - 只接受jpg,jpeg,png
            String[] acceptType = new String[]{"jpg", "jpeg", "png"};
            if (Arrays.stream(acceptType).noneMatch(type::contains)) {
                return Response.fail(5, "不支持的图片格式");
            }
        } catch (IOException e) {
            return Response.fail(3, "获取文件类型时发生错误");
        }
        //储存图片并更改用户表中的记录
        try {
            if (userInfoService.updateAvatar(userId, file.getInputStream(), type)) {
                return Response.ok(null);
            } else {
                return Response.fail(7, "内部错误");
            }
        } catch (IOException e) {
            return Response.fail(7, "内部错误");
        }
    }

    //编辑个人资料
    @PostMapping("/updateInfo")
    public Response updateInfo(@RequestBody UpdateInfoVO vo, @RequestHeader("Authorization") String token) {
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //判断昵称空白
        if (StrUtil.isBlank(vo.getNickname())) {
            return Response.fail(1, "昵称不能为空");
        }
        //判断昵称重复
        if (userInfoService.isNicknameUsed(vo.getNickname(), userId)) {
            return Response.fail(3, "昵称已被使用");
        }
        //判断描述空白，空白则帮用户设置为无
        if (StrUtil.isBlank(vo.getDescription())) {
            vo.setDescription("无");
        }
        //传入修改
        if (userInfoService.updateInfo(userId, vo.getNickname(), vo.getDescription())) {
            return Response.ok();
        } else {
            return Response.fail(5, "内部更新失败");
        }

    }

    //重置密码
    @PostMapping("/updatePassword")
    public Response updatePassword(@RequestBody UpdatePasswordVO vo, @RequestHeader("Authorization") String token) {
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //判断为空
        if (StrUtil.hasBlank(vo.getOldPassword(), vo.getNewPassword(), vo.getConfirmNewPassword())) {
            return Response.fail(1, "所有字段均不能为空");
        }
        //判断确认密码一致
        if (!vo.getNewPassword().equals(vo.getConfirmNewPassword())) {
            return Response.fail(3, "两次密码不匹配");
        }
        //判断密码长度
        if (vo.getNewPassword().length() < 8) {
            return Response.fail(4, "密码不能少于8个字符");
        }
        //判断旧密码是否正确
        if (!authService.isPasswordCorrect(userId, vo.getOldPassword())) {
            return Response.fail(5, "原密码错误");
        }
        //判断新旧密码是否相同
        if (vo.getOldPassword().equals(vo.getNewPassword())) {
            return Response.fail(7, "新旧密码相同");
        }
        //传入修改
        if (authService.updatePassword(userId, vo.getNewPassword())) {
            //重置登录
            jwtAuthUtil.revoke(token);
            return Response.ok();
        } else {
            return Response.fail(9, "内部更新失败");
        }
    }


}
