package soda.npe.serviceuser.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import soda.npe.common.controller.Response;
import soda.npe.common.entity.UserInfo;
import soda.npe.common.utils.JwtAuthUtil;
import soda.npe.serviceuser.service.UserInfoService;

@RequestMapping("/operation")
public class OperationController {

    @Resource
    private UserInfoService userInfoService;

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
        String userId = jwtAuthUtil.getPayload(token).getStr("userId");
        //获取userinfo
        UserInfo found = userInfoService.getById(userId);
        //返回
        return Response.ok(null, found);
    }

    //上传头像

    //编辑个人资料

    //重置密码
    

}
