package soda.npe.servicequestion.controller;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.common.controller.Response;
import soda.npe.common.entity.QuestionInfo;
import soda.npe.servicequestion.service.QuestionAnswerService;
import soda.npe.servicequestion.service.QuestionInfoService;
import soda.npe.servicequestion.service.QuestionReplyService;
import soda.npe.servicequestion.service.UserQuestionSubscriptionService;

/**
 * 处理问题相关的（包括问题、首页的问题列表、问题的回答、回答的回复）内容读取控制器
 */
@RestController
//@RequestMapping("/")
public class DataFetchController {
    @Resource
    private QuestionAnswerService questionAnswerService;
    @Resource
    private QuestionInfoService questionInfoService;
    @Resource
    private QuestionReplyService questionReplyService;

    @Resource
    private UserQuestionSubscriptionService userQuestionSubscriptionService;

    /**
     * 以从新到就的顺序，获取指定用户发表过的问题
     *
     * @param userId 用户ID
     * @param page   页
     * @return 问题列表
     */
    @GetMapping("/getQuestionInfoPublishedBy")
    public Response getQuestionInfoPublishedBy(Long userId, Integer page) {
        if (userId == null) return Response.fail(1, "未指定用户");
        if (page == null || page < 1) page = 1;
        return Response.ok(null, questionInfoService.getInfoPublishedBy(userId, page));
    }

    /**
     * 以从新到旧的顺序，获取指定用户发表过的回答
     *
     * @param userId 用户ID
     * @param page   页
     * @return 回答列表
     */
    @GetMapping("/getAnswerPublishedBy")
    public Response getAnswerPublishedBy(Long userId, Integer page) {
        if (userId == null) return Response.fail(1, "未指定用户");
        if (page == null || page < 1) page = 1;
        return Response.ok(null, questionAnswerService.getAnswerPublishedBy(userId, page));
    }

    /**
     * 获取回答下的回复
     *
     * @param answerId 回答ID
     * @param page     页
     * @return 回复列表
     */
    @GetMapping("/getReplyOfAnswer")
    public Response getReplyOfAnswer(Long answerId, Integer page) {
        if (answerId == null) return Response.fail(1, "未指定回答");
        if (page == null || page < 1) page = 1;
        return Response.ok(null, questionReplyService.getOfAnswer(answerId, page));
    }

    /**
     * 获取问题的基本信息，如作者、发布时间、标题等
     *
     * @param questionId 问题ID
     * @return QuestionInfo对象
     */
    @GetMapping("/getQuestionInfo")
    public Response getQuestionInfo(Long questionId) {
        if (questionId == null) return Response.fail(1, "questionId is null");
        QuestionInfo one = questionInfoService.getById(questionId);
        if (one == null) return Response.fail(1, "指定ID没有找到对象");
        return Response.ok(null, one);
    }

    /**
     * 获取问题的正文
     *
     * @param questionId 问题ID
     * @return 正文的Markdown字符串
     */
    @GetMapping("/getQuestionText")
    public Response getQuestionText(Long questionId) {
        if (questionId == null) return Response.fail(1, "questionId is null");
        String text = questionInfoService.getText(questionId);
        if (text == null) return Response.fail(1, "没有找到对象");
        else return Response.ok(null, text);
    }

    /**
     * 获取问题回答数量
     *
     * @param questionId 问题ID
     * @return 回答数量
     */
    @GetMapping("/getAnswerAmountOf")
    public Response getAnswerAmountOf(Long questionId) {
        if (questionId == null) return Response.fail(1, "questionId is null");
        return Response.ok(null, questionAnswerService.getAnswerAmountOf(questionId));
    }

    /**
     * 获取问题订阅人数
     *
     * @param questionId 问题ID
     * @return 订阅人数
     */
    @GetMapping("/getSubscriptionAmountOf")
    public Response getSubscriptionAmountOf(Long questionId) {
        if (questionId == null) return Response.fail(1, "questionId is null");
        return Response.ok(null, userQuestionSubscriptionService.getSubscriptionAmountOf(questionId));
    }

    /**
     * 以时间顺序获取问题某一页的答案
     *
     * @param questionId 问题ID
     * @param page       页
     * @param isAsc      asc
     * @return 答案实体列表
     */
    @GetMapping("/getAnswerByTimeOf")
    public Response getAnswerByTimeOf(Long questionId, Integer page, Boolean isAsc) {
        if (questionId == null) return Response.fail(1, "未指定问题");
        if (page == null || page < 1) page = 1;
        return Response.ok(null, questionAnswerService.getByTimeOf(questionId, page, isAsc));
    }

    /**
     * 以点赞数量顺序获取问题某一页的答案
     *
     * @param questionId 问题ID
     * @param page       页
     * @param isAsc      asc
     * @return 答案实体列表
     */
    @GetMapping("/getAnswerByApprovalOf")
    public Response getAnswerByApprovalOf(Long questionId, Integer page, Boolean isAsc) {
        if (questionId == null) return Response.fail(1, "未指定问题");
        if (page == null || page < 1) page = 1;
        return Response.ok(null, questionAnswerService.getByApprovalOf(questionId, page, isAsc));
    }

    /**
     * 用于在发布问题时，提供一个类型推荐
     *
     * @param input 输入
     * @return 0~8个推荐类型
     */
    @GetMapping("/getCategoriesSuggestion")
    public Response getCategoriesSuggestion(String input) {
        if (StrUtil.isBlank(input)) return Response.fail(1, "未输入");
        return Response.ok(questionInfoService.getCategoriesSuggestion(input));
    }
}
