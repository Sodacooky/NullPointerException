package soda.npe.servicequestion.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.common.controller.RestResponse;
import soda.npe.common.entity.QuestionAnswer;
import soda.npe.common.entity.QuestionInfo;
import soda.npe.servicequestion.service.SearchService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/search")
public class QuestionSearchController {

    @Resource
    private SearchService searchService;

    /**
     * 基于问题信息和详情（一楼）搜索
     *
     * @param keyword 关键词
     * @param page    页码，from 1
     * @param order   顺序，time_desc|time_asc|subscription_amount_asc|..desc
     * @return 找到的问题信息
     */
    @GetMapping("/questionInfoSearch")
    public RestResponse questionInfoSearch(String keyword, Integer page, String order) {
        List<QuestionInfo> questionInfos = searchService.doQuestionInfoSearch(keyword, page, order);
        return RestResponse.ok(null, questionInfos);
    }

    /**
     * 搜索答案
     *
     * @param keyword 关键词
     * @param page    页码，from 1
     * @param order   顺序，time_desc|time_asc|approval_amount_asc|..desc
     * @return 找到的回答
     */
    @GetMapping("/answerSearch")
    public RestResponse answerSearch(String keyword, Integer page, String order) {
        List<QuestionAnswer> questionAnswers = searchService.doAnswerSearch(keyword, page, order);
        return RestResponse.ok(null, questionAnswers);
    }
}
