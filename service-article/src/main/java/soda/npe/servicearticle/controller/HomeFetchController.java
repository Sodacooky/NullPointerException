package soda.npe.servicearticle.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.common.controller.Response;
import soda.npe.servicearticle.service.ArticleService;

@RestController
@RequestMapping("/public/home")
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
    public Response latest(Integer page, String queryTime) {
        //check params
        if (page == null || page < 1) page = 1;
        if (StrUtil.isBlank(queryTime)) return Response.fail(1, "queryTime缺失");
        if (DateUtil.parse(queryTime) == null) Response.fail(2, "queryTime格式错误");
        //do fetch
        return Response.ok(null, articleService.getHomeLatest(page, DateUtil.parse(queryTime).toJdkDate()));
    }

    /**
     * 获取首页问题周榜
     *
     * @return QuestionInfoPreviewVO列表
     */
    @GetMapping("/weekly")
    public Response weekly() {
        //do fetch
        return Response.ok(null, articleService.getHomeWeekly());
    }

    /**
     * 获取首页问题月榜
     *
     * @return QuestionInfoPreviewVO列表
     */
    @GetMapping("/monthly")
    public Response monthly() {
        //do fetch
        return Response.ok(null, articleService.getHomeMonthly());
    }

}
