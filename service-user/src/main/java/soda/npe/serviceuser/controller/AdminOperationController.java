package soda.npe.serviceuser.controller;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import soda.npe.common.controller.Response;
import soda.npe.common.entity.UserNotice;
import soda.npe.serviceuser.service.UserInfoService;
import soda.npe.serviceuser.service.UserNoticeService;
import soda.npe.serviceuser.vo.ModifyUserVO;

import java.util.Date;

@RestController
@RequestMapping("/auth/admin")
public class AdminOperationController {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private UserNoticeService userNoticeService;

    @PostMapping("/modify")
    public Response modify(@RequestBody ModifyUserVO vo) {
        //判断用户ID存在
        if (vo.getId() == null || userInfoService.getById(vo.getId()) == null) {
            return Response.fail(1, "用户ID未指定或不存在");
        }
        //判断内容空白
        if (StrUtil.hasBlank(vo.getNickname(), vo.getDescription(), vo.getAvatar())) {
            return Response.fail(3, "所有字段不能为空");
        }
        //判断昵称重复
        if (userInfoService.isNicknameUsed(vo.getNickname(), vo.getId())) {
            return Response.fail(5, "昵称已经被使用");
        }
        //传入修改
        if (userInfoService.adminUpdate(vo)) {
            UserNotice userNotice = new UserNotice();
            userNotice.setGoalUserId(vo.getId());
            userNotice.setType("system");
            userNotice.setTitle("您的个人信息以被管理员修改");
            userNotice.setText("请确保您的个人信息符合相关规定。");
            userNotice.setTime(new Date());
            userNotice.setIsRead(0);
            userNoticeService.save(userNotice);//ignore result
            return Response.ok();
        } else return Response.fail(7, "内部修改失败");
    }

    @GetMapping("/ban")
    public Response ban(Long userId) {
        //判断用户ID存在
        if (userId == null || userInfoService.getById(userId) == null) {
            return Response.fail(1, "用户ID未指定或不存在");
        }
        //执行
        if (userInfoService.getById(userId).getIsBanned() == 1) {
            return Response.fail(3, "用户已经被封禁了");
        }
        if (userInfoService.doBan(userId)) return Response.ok();
        else return Response.fail(5, "操作失败");
    }

    @GetMapping("/unban")
    public Response unban(Long userId) {
        //判断用户ID存在
        if (userId == null || userInfoService.getById(userId) == null) {
            return Response.fail(1, "用户ID未指定或不存在");
        }
        //执行
        if (userInfoService.getById(userId).getIsBanned() == 0) {
            return Response.fail(3, "用户没有被封禁");
        }
        if (userInfoService.doUnban(userId)) return Response.ok();
        else return Response.fail(3, "操作失败");
    }

}
