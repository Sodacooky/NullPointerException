package soda.npe.serviceuser.controller;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.common.controller.Response;
import soda.npe.serviceuser.service.UserNoticeService;
import soda.npe.serviceuser.vo.SendCustomNoticeVO;

@RestController
@RequestMapping("/auth/admin/")
public class AdminNoticeController {

    @Resource
    private UserNoticeService userNoticeService;

    @PostMapping("/sendCustomNotice")
    public Response sendCustomNotice(@RequestBody SendCustomNoticeVO vo) {
        if (StrUtil.hasBlank(vo.getGoalUserId(), vo.getText(), vo.getTitle())) {
            return Response.fail(1, "所有字段不能为空");
        }
        String[] goalUserIdArr = vo.getGoalUserId().split(" ");
        if (goalUserIdArr.length == 0) {
            return Response.fail(3, "用户列表无法读取到至少一个用户ID");
        }
        int count = userNoticeService.sendCustomNotice(goalUserIdArr, vo.getTitle(), vo.getText());
        return Response.ok("已为" + count + "个用户发送消息", null);
    }


}
