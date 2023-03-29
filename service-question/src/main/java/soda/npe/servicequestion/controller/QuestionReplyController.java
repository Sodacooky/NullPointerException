package soda.npe.servicequestion.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.common.controller.RestResponse;
import soda.npe.servicequestion.service.QuestionReplyService;

/**
 * 对回答的回复相关接口
 */
@RestController
@RequestMapping("/reply")
public class QuestionReplyController {

    @Resource
    private QuestionReplyService questionReplyService;

    @GetMapping("/getOfAnswer")
    public RestResponse getOfAnswer(Long answerId, Integer page) {
        if (answerId == null) return RestResponse.fail(1, "未指定回答");
        if (page == null || page < 1) page = 1;
        return RestResponse.ok(null, questionReplyService.getOfAnswer(answerId, page));
    }

}
