package soda.npe.servicequestion.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.common.controller.RestResponse;
import soda.npe.common.entity.QuestionInfo;
import soda.npe.servicequestion.service.QuestionInfoService;

/**
 * 问题信息相关控制器
 */
@RestController
@Slf4j
@RequestMapping("/info")
public class QuestionInfoController {

    @Resource
    private QuestionInfoService questionInfoService;

    /**
     * 获取问题的基本信息，如作者、发布时间、标题等
     *
     * @param questionId 问题ID
     * @return QuestionInfo对象
     */
    @GetMapping("/get")
    public RestResponse getInfo(Long questionId) {
        if (questionId == null) return RestResponse.fail(1, "questionId is null");
        QuestionInfo one = questionInfoService.getById(questionId);
        if (one == null) return RestResponse.fail(1, "指定ID没有找到对象");
        return RestResponse.ok(null, one);
    }

    /**
     * 获取问题的正文
     *
     * @param questionId 问题ID
     * @return 正文的Markdown字符串
     */
    @GetMapping("/getText")
    public RestResponse getText(Long questionId) {
        if (questionId == null) return RestResponse.fail(1, "questionId is null");
        String text = questionInfoService.getText(questionId);
        if (text == null) return RestResponse.fail(1, "没有找到对象");
        else return RestResponse.ok(null, text);
    }
    
    /**
     * 获取问题回答数量
     *
     * @param questionId 问题ID
     * @return 回答数量
     */
    @GetMapping("/getAnswerAmount")
    public RestResponse getAnswerAmount(Long questionId) {
        if (questionId == null) return RestResponse.fail(1, "questionId is null");
        return RestResponse.ok(null, questionInfoService.getAnswerAmount(questionId));
    }

    /**
     * 获取问题订阅人数
     *
     * @param questionId 问题ID
     * @return 订阅人数
     */
    @GetMapping("/getSubscriptionAmount")
    public RestResponse getSubscriptionAmount(Long questionId) {
        if (questionId == null) return RestResponse.fail(1, "questionId is null");
        return RestResponse.ok(null, questionInfoService.getSubscriptionAmount(questionId));
    }
}
