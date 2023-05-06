package soda.npe.servicemiscellaneous.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import soda.npe.common.controller.Response;
import soda.npe.servicemiscellaneous.service.AdvertisementService;
import soda.npe.servicemiscellaneous.vo.AddAdvertisementVO;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping("/auth/admin")
public class AdminAdvManageController {

    @Resource
    private AdvertisementService advertisementService;

    @GetMapping("/removeAdv")
    public Response removeAdv(Long advertisementId) {
        if (advertisementId == null) return Response.fail(1, "未指定ID");
        if (advertisementService.getById(advertisementId) == null) return Response.fail(3, "ID指定的对象不存在");
        if (advertisementService.removeAdv(advertisementId)) {
            return Response.ok();
        } else {
            return Response.fail(5, "内部删除失败");
        }
    }

    @PostMapping("/addAdv")
    public Response addAdv(@RequestBody AddAdvertisementVO vo) {
        if (StrUtil.hasBlank(vo.getUrl(), vo.getImage())) return Response.fail(1, "所有字段均不能为空");
        if (advertisementService.addAdv(vo)) return Response.ok();
        else return Response.fail(3, "内部创建失败");
    }

    @PostMapping("/uploadAdvImage")
    public Response uploadAdvImage(MultipartFile file) {
        //判断文件大小
        if (file.getSize() > 2 * 1024 * 1024) return Response.fail(1, "图片超过2MB");
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
            String filename = advertisementService.uploadImage(file.getInputStream(), type);
            if (filename != null) {
                return Response.ok(filename);
            } else {
                return Response.fail(7, "内部错误");
            }
        } catch (IOException e) {
            return Response.fail(7, "内部错误");
        }
    }

}
