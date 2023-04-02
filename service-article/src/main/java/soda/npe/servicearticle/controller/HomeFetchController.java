package soda.npe.servicearticle.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.common.controller.RestResponse;
import soda.npe.servicearticle.service.ArticleService;

import java.util.Date;

@RestController
@RequestMapping("/home")
public class HomeFetchController {

    @Resource
    private ArticleService articleService;

    /**
     * 获取首页最新文章列表
     *
     * @param page      页码，from 1
     * @param queryTime 调用获取第一页时的时间，用于控制分页不要出现重复条目
     * @return QuestionInfoPreviewVO列表
     */
    @GetMapping("/latest")
    public RestResponse latest(Integer page, Date queryTime) {
        //check params
        if (page == null || page < 1) page = 1;
        if (queryTime == null) queryTime = new Date();
        //do fetch
        return RestResponse.ok(null, articleService.getHomeLatest(page, queryTime));
    }

    /**
     * 获取首页问题周榜
     *
     * @return QuestionInfoPreviewVO列表
     */
    @GetMapping("/weekly")
    public RestResponse weekly() {
        //do fetch
        return RestResponse.ok(null, articleService.getHomeWeekly());
    }

    /**
     * 获取首页问题月榜
     *
     * @return QuestionInfoPreviewVO列表
     */
    @GetMapping("/monthly")
    public RestResponse monthly() {
        //do fetch
        return RestResponse.ok(null, articleService.getHomeMonthly());
    }

}
