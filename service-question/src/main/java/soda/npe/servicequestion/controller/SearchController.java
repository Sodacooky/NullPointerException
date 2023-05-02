package soda.npe.servicequestion.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.common.controller.Response;
import soda.npe.servicequestion.service.QuestionAnswerService;
import soda.npe.servicequestion.service.QuestionInfoService;

/**
 * 处理问题相关的搜索，包括问题搜索和答案搜索
 */
@RestController
@RequestMapping("/public/search")
public class SearchController {

    @Resource
    private QuestionInfoService questionInfoService;

    @Resource
    private QuestionAnswerService questionAnswerService;

    /**
     * 按照时间顺序搜索问题，从问题的标题、标签、第一个Answer（正文）中匹配
     *
     * @param keyword 关键词
     * @param page    页码 from 1
     * @param isAsc   是否时间升序（从旧到新）
     * @return 当前页下，找到的问题实体列表
     */
    @GetMapping("/infoByTime")
    public Response infoByTime(String keyword, Integer page, Boolean isAsc) {
        if (page == null || page < 1) page = 1;
        if (isAsc == null) isAsc = false;
        return Response.ok(null, questionInfoService.searchByTime(keyword, page, isAsc));
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
    public Response infoBySubscriptionAmount(String keyword, Integer page, Boolean isAsc) {
        if (page == null || page < 1) page = 1;
        if (isAsc == null) isAsc = false;
        return Response.ok(null, questionInfoService.searchBySubscriptionAmount(keyword, page, isAsc));
    }


    @GetMapping("/infoByAnswerAmount")
    public Response infoByAnswerAmount(String keyword, Integer page, Boolean isAsc) {
        if (page == null || page < 1) page = 1;
        if (isAsc == null) isAsc = false;
        return Response.ok(null, questionInfoService.searchByAnswerAmount(keyword, page, isAsc));
    }

    /**
     * 按照时间顺序搜索答案
     *
     * @param keyword 关键词
     * @param page    页
     * @param isAsc   是否时间升序（从旧到新）
     * @return 当前页下，找到的回答实体列表
     */
    @GetMapping("/answerByTime")
    public Response answerByTime(String keyword, Integer page, Boolean isAsc) {
        if (page == null || page < 1) page = 1;
        if (isAsc == null) isAsc = false;
        return Response.ok(null, questionAnswerService.searchByTime(keyword, page, isAsc));
    }

    /**
     * 按照点赞数量顺序搜索答案
     *
     * @param keyword 关键词
     * @param page    页
     * @param isAsc   是否升序（从少到多）
     * @return 当前页下，找到的回答实体列表
     */
    @GetMapping("/answerByApproval")
    public Response answerByApproval(String keyword, Integer page, Boolean isAsc) {
        if (page == null || page < 1) page = 1;
        if (isAsc == null) isAsc = false;
        return Response.ok(null, questionAnswerService.searchByApproval(keyword, page, isAsc));
    }

}
