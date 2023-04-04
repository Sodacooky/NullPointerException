package soda.npe.servicequestion.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.common.controller.RestResponse;
import soda.npe.servicequestion.service.QuestionInfoService;

/**
 * 首页问题相关的三个列表的控制器
 */
@RestController
@RequestMapping("/home")
public class HomeFetchController {

    @Resource
    private QuestionInfoService questionInfoService;

    /**
     * 获取首页最新问题列表
     *
     * @param page      页码，from 1
     * @param queryTime 调用获取第一页时的时间，用于控制分页不要出现重复条目
     * @return QuestionInfoPreviewVO列表
     */
    @GetMapping("/latest")
    public RestResponse latest(Integer page, String queryTime) {
        //check params
        if (page == null || page < 1) page = 1;
        if (StrUtil.isBlank(queryTime)) return RestResponse.fail(1, "queryTime缺失");
        if (DateUtil.parse(queryTime) == null) RestResponse.fail(2, "queryTime格式错误");
        //do fetch
        return RestResponse.ok(null, questionInfoService.getHomeLatest(page, DateUtil.parse(queryTime)));
    }

    /**
     * 获取首页问题周榜
     *
     * @return QuestionInfoPreviewVO列表
     */
    @GetMapping("/weekly")
    public RestResponse weekly() {
        //do fetch
        return RestResponse.ok(null, questionInfoService.getHomeWeekly());
    }

    /**
     * 获取首页问题月榜
     *
     * @return QuestionInfoPreviewVO列表
     */
    @GetMapping("/monthly")
    public RestResponse monthly() {
        //do fetch
        return RestResponse.ok(null, questionInfoService.getHomeMonthly());
    }

}
