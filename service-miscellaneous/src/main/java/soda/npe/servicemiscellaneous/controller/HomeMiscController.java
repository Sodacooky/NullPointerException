package soda.npe.servicemiscellaneous.controller;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.common.controller.Response;
import soda.npe.servicemiscellaneous.service.HomeMiscService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 首页其他数据展示控制器
 */
@RestController
@RequestMapping("/home")
public class HomeMiscController {

    @Resource
    private HomeMiscService homeMiscService;

    @Value("${npe.ads-image-path:./ads/}")
    private String adsImagePath;

    /**
     * 获取网站状况
     *
     * @return 首页右下角网站状况的四个值
     */
    @GetMapping("/siteState")
    public Response siteState() {
        return Response.ok(homeMiscService.getSiteState());
    }


    /**
     * 获取广告
     *
     * @return 广告列表
     */
    @GetMapping("/adv")
    public Response ads() {
        return Response.ok(homeMiscService.getAds());
    }

    /**
     * 获取广告图片字节流
     *
     * @param filename 广告文件名，获取广告接口响应对象中的image
     * @return 图片字节流
     */
    @GetMapping("/advImage/{filename}")
    public byte[] adsImage(@PathVariable("filename") String filename) {
        //load file and check existence
        File toGetAvatarFile = new File(adsImagePath, filename + ".jpg");
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
     * 获取首页热门分类
     *
     * @return 分类名称、数量实体
     */
    @GetMapping("/hotCategories")
    public Response hotCategories() {
        return Response.ok(homeMiscService.getHotCategories());
    }


    /**
     * 获取首页公告
     *
     * @return 公告列表
     */
    @GetMapping("/announcement")
    public Response announcement() {
        return Response.ok(homeMiscService.getAnnouncement());
    }
}
