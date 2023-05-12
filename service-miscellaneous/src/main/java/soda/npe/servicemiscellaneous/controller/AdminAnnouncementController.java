package soda.npe.servicemiscellaneous.controller;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import soda.npe.common.controller.Response;
import soda.npe.servicemiscellaneous.service.AnnouncementService;
import soda.npe.servicemiscellaneous.vo.AddAnnouncementVO;

@RestController
@RequestMapping("/auth/admin")
public class AdminAnnouncementController {

    @Resource
    private AnnouncementService announcementService;

    @PostMapping("/addAnnouncement")
    public Response addAnnouncement(@RequestBody AddAnnouncementVO vo) {
        if (StrUtil.hasBlank(vo.getText(), vo.getTitle())) {
            return Response.fail(1, "所有字段不能为空");
        }
        if (announcementService.add(vo)) {
            return Response.ok();
        } else {
            return Response.fail(3, "内部添加失败");
        }
    }

    @GetMapping("/removeAnnouncement")
    public Response removeAnnouncement(Long announcementId) {
        if (announcementId == null) {
            return Response.fail(1, "未指定ID");
        }
        if (announcementService.getById(announcementId) == null) {
            return Response.fail(3, "ID所指定对象不存在");
        }
        if (announcementService.removeById(announcementId)) {
            return Response.ok();
        } else {
            return Response.fail(5, "内部删除失败");
        }
    }


}
