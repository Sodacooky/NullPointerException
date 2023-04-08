package soda.npe.serviceuser.controller;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.common.controller.Response;
import soda.npe.common.entity.UserInfo;
import soda.npe.common.utils.JwtAuthUtil;
import soda.npe.serviceuser.service.UserInfoService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 处理用户信息的获取
 */
@RestController
//@RequestMapping("/")
public class DataFetchController {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private JwtAuthUtil jwtAuthUtil;

    @Value("${npe.avatar-path:./avatar/}")
    private String avatarPath;

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @GetMapping("/getUserInfo")
    public Response getUserInfo(Long userId) {
        if (userId == null) return Response.fail(1, "未指定用户");
        UserInfo found = userInfoService.getById(userId);
        if (found == null) return Response.fail(2, "未找到用户");
        return Response.ok(null, found);
    }

    /**
     * 获取头像
     *
     * @param filename 文件名，不带文件后缀名，都是.jpg
     * @return 字节流
     */
    @GetMapping("/avatar/{filename}")
    public byte[] getUserAvatar(@PathVariable("filename") String filename) {
        //load file and check existence
        File toGetAvatarFile = new File(avatarPath, filename + ".jpg");
        if (!toGetAvatarFile.exists() || !toGetAvatarFile.isFile())
            return Response.fail(1, "文件不存在").toString().getBytes();
        //read as bytes
        try (var stream = new FileInputStream(toGetAvatarFile)) {
            return stream.readAllBytes();
        } catch (IOException e) {
            return Response.fail(2, "读取文件出错").toString().getBytes();
        }
    }

    /**
     * 获取是否登录
     *
     * @param token jwt
     * @return 是否成功Boolean
     */
    @GetMapping("/getLoginState")
    public Response getLoginState(String token) {
        return Response.ok(null, jwtAuthUtil.validation(token));
    }
}
