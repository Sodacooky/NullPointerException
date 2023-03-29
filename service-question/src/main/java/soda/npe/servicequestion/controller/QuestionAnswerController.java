package soda.npe.servicequestion.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.common.controller.RestResponse;

/**
 * 回答相关接口
 */
@RestController
@RequestMapping("/answer")
public class QuestionAnswerController {

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

    }


}
