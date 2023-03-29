package soda.npe.servicequestion.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.common.controller.RestResponse;
import soda.npe.servicequestion.service.QuestionAnswerService;

/**
 * 回答相关接口
 */
@RestController
@RequestMapping("/answer")
public class QuestionAnswerController {

    @Resource
    private QuestionAnswerService questionAnswerService;

    /**
     * 以时间顺序获取问题某一页的答案
     *
     * @param questionId 问题ID
     * @param page       页
     * @param isAsc      asc
     * @return 答案实体列表
     */
    @GetMapping("/getOfQuestionByTime")
    public RestResponse getOfQuestionByTime(Long questionId, Integer page, Boolean isAsc) {
        if (questionId == null) return RestResponse.fail(1, "未指定问题");
        if (page == null || page < 1) page = 1;
        return RestResponse.ok(null, questionAnswerService.getOfQuestionByTime(questionId,page,isAsc));
    }

    /**
     * 以点赞数量顺序获取问题某一页的答案
     *
     * @param questionId 问题ID
     * @param page       页
     * @param isAsc      asc
     * @return 答案实体列表
     */
    @GetMapping("/getOfQuestionByApproval")
    public RestResponse getOfQuestionByApproval(Long questionId, Integer page, Boolean isAsc) {
        if (questionId == null) return RestResponse.fail(1, "未指定问题");
        if (page == null || page < 1) page = 1;
        return RestResponse.ok(null, questionAnswerService.getOfQuestionByApproval(questionId,page,isAsc));
    }


}
