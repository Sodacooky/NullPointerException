package soda.npe.servicequestion.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.common.controller.RestResponse;
import soda.npe.servicequestion.service.QuestionAnswerService;
import soda.npe.servicequestion.service.QuestionInfoService;

/**
 * 用以获取用户相关的问题信息
 */
@RestController
@RequestMapping("/user")
public class UserRelevanceQuestionController {

    @Resource
    private QuestionInfoService questionInfoService;

    @Resource
    private QuestionAnswerService questionAnswerService;

    /**
     * 以从新到就的顺序，获取指定用户发表过的问题
     *
     * @param userId 用户ID
     * @param page   页
     * @return 问题列表
     */
    @GetMapping("/getInfoPublishedBy")
    public RestResponse getInfoPublishedBy(Long userId, Integer page) {
        if (userId == null) return RestResponse.fail(1, "未指定用户");
        if (page == null || page < 1) page = 1;
        return RestResponse.ok(null, questionInfoService.getInfoPublishedBy(userId, page));
    }

    /**
     * 以从新到就的顺序，获取指定用户发表过的回答
     *
     * @param userId 用户ID
     * @param page   页
     * @return 回答列表
     */
    @GetMapping("/getAnswerPublishedBy")
    public RestResponse getAnswerPublishedBy(Long userId, Integer page) {
        if (userId == null) return RestResponse.fail(1, "未指定用户");
        if (page == null || page < 1) page = 1;
        return RestResponse.ok(null, questionAnswerService.getAnswerPublishedBy(userId, page));
    }

}
