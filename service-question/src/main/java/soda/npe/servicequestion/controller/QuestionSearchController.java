package soda.npe.servicequestion.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.common.controller.RestResponse;
import soda.npe.servicequestion.service.QuestionInfoService;

@RestController
@Slf4j
@RequestMapping("/search")
public class QuestionSearchController {

    @Resource
    private QuestionInfoService questionInfoService;

    /**
     * 按照时间顺序搜索问题，从问题的标题、标签、第一个Answer（正文）中匹配
     *
     * @param keyword 关键词
     * @param page    页码
     * @param isAsc   是否时间升序（从旧到新）
     * @return 当前页下，找到的问题实体列表
     */
    @GetMapping("/infoByTime")
    public RestResponse infoByTime(String keyword, Integer page, Boolean isAsc) {
        if (page == null || page < 1) page = 1;
        if (isAsc == null) isAsc = false;
        return RestResponse.ok(null, questionInfoService.searchInfoByTime(keyword, page, isAsc));
    }

    /**
     * 按照订阅用户数量顺序搜索问题，从问题的标题、标签、第一个Answer（正文）中匹配
     *
     * @param keyword 关键词
     * @param page    页码
     * @param isAsc   是否数量升序（从少到多）
     * @return 当前页下，找到的问题实体列表
     */
    @GetMapping("/infoBySubscriptionAmount")
    public RestResponse infoBySubscriptionAmount(String keyword, Integer page, Boolean isAsc) {
        if (page == null || page < 1) page = 1;
        if (isAsc == null) isAsc = false;
        return RestResponse.ok(null, questionInfoService.searchInfoBySubscriptionAmount(keyword, page, isAsc));
    }


}
