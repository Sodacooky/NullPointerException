package soda.npe.servicequestion.controller;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import soda.npe.common.controller.RestResponse;
import soda.npe.common.utils.JwtAuthUtil;
import soda.npe.servicequestion.service.*;
import soda.npe.servicequestion.vo.AnswerPublishVO;
import soda.npe.servicequestion.vo.QuestionPublishVO;
import soda.npe.servicequestion.vo.ReplyPublishVO;


/**
 * 对问题和答案发布、修改、点赞订阅等操作
 */
@RestController
@RequestMapping("/operation")
public class OperationController {

    @Resource
    private JwtAuthUtil jwtAuthUtil;

    @Resource
    private UserQuestionSubscriptionService userQuestionSubscriptionService;

    @Resource
    private ApprovalAnswerService approvalAnswerService;

    @Resource
    private QuestionInfoService questionInfoService;

    @Resource
    private QuestionAnswerService questionAnswerService;

    @Resource
    private QuestionReplyService questionReplyService;

    /**
     * 用户订阅问题，从Header中获取JWT并识别当前用户
     *
     * @param questionId 要订阅的问题ID
     * @param token      Header中的JWT
     * @return 是否成功
     */
    @GetMapping("/subscription")
    public RestResponse subscription(Long questionId, @RequestHeader("Authorization") String token) {
        if (questionId == null) return RestResponse.fail(1, "未指定问题");
        //能到达这里，token已经是被校验过得了，我们只获取当前用户ID
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //做
        if (userQuestionSubscriptionService.subscription(questionId, userId)) return RestResponse.ok(null, null);
        else return RestResponse.fail(2, "可能已经订阅或者问题不存在");
    }


    /**
     * 用户取消订阅问题，从Header中获取JWT并识别当前用户
     *
     * @param questionId 要订阅的问题ID
     * @param token      Header中的JWT
     * @return 是否成功
     */
    @GetMapping("/unSubscription")
    public RestResponse unSubscription(Long questionId, @RequestHeader("Authorization") String token) {
        if (questionId == null) return RestResponse.fail(1, "未指定问题");
        //能到达这里，token已经是被校验过得了，我们只获取当前用户ID
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //做
        if (userQuestionSubscriptionService.unSubscription(questionId, userId)) return RestResponse.ok(null, null);
        else return RestResponse.fail(2, "可能未订阅或者问题不存在");
    }

    /**
     * 点赞回答
     *
     * @param answerId 回答ID
     * @param token    Header中的JWT
     * @return 是否成功
     */
    @GetMapping("/approve")
    public RestResponse approve(Long answerId, @RequestHeader("Authorization") String token) {
        if (answerId == null) return RestResponse.fail(1, "未指定回答");
        //能到达这里，token已经是被校验过得了，我们只获取当前用户ID
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //做
        if (approvalAnswerService.approve(answerId, userId)) return RestResponse.ok(null, null);
        else return RestResponse.fail(2, "可能已点赞或答案不存在");
    }

    /**
     * 取消点赞回答
     *
     * @param answerId 回答ID
     * @param token    Header中的JWT
     * @return 是否成功
     */
    @GetMapping("/unApprove")
    public RestResponse unApprove(Long answerId, @RequestHeader("Authorization") String token) {
        if (answerId == null) return RestResponse.fail(1, "未指定回答");
        //能到达这里，token已经是被校验过得了，我们只获取当前用户ID
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //做
        if (approvalAnswerService.unApprove(answerId, userId)) return RestResponse.ok(null, null);
        else return RestResponse.fail(2, "可能未点赞或答案不存在");
    }

    /**
     * 问题的发布
     *
     * @param questionPublishVO 问题发布的内容，包括标题、分类、正文
     * @param token             Header中的JWT
     * @return 是否成功，成功data中附带刚刚发布问题的ID
     */
    @PostMapping("/publishQuestion")
    public RestResponse publishQuestion(@RequestBody QuestionPublishVO questionPublishVO,
                                        @RequestHeader("Authorization") String token) {
        if (questionPublishVO == null) {
            return RestResponse.fail(1, "缺少参数");
        }
        if (StrUtil.hasBlank(questionPublishVO.getText(), questionPublishVO.getTitle(), questionPublishVO.getCategory())) {
            return RestResponse.fail(2, "存在为空参数");
        }
        //能到达这里，token已经是被校验过得了，我们只获取当前用户ID
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //发布
        Long publishInfoId = questionInfoService.publish(userId, questionPublishVO);
        if (publishInfoId == null) {
            return RestResponse.fail(3, "发布失败，服务器内部错误");
        } else {
            return RestResponse.ok(null, publishInfoId);
        }
    }

    /**
     * 发布回答
     *
     * @param answerPublishVO 回答发布的内容，包括目标问题ID、回复内容
     * @param token           header中的jwt
     * @return 是否成功
     */
    @PostMapping("/publishAnswer")
    public RestResponse publishAnswer(@RequestBody AnswerPublishVO answerPublishVO,
                                      @RequestHeader("Authorization") String token) {
        if (answerPublishVO == null) {
            return RestResponse.fail(1, "缺少参数");
        }
        if (StrUtil.isBlank(answerPublishVO.getText()) || answerPublishVO.getQuestionId() == null) {
            return RestResponse.fail(2, "存在为空参数");
        }
        //能到达这里，token已经是被校验过得了，我们只获取当前用户ID
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //发布
        if (!questionAnswerService.publish(userId, answerPublishVO)) {
            return RestResponse.fail(3, "发布失败，服务器内部错误");
        } else {
            return RestResponse.ok(null, null);
        }
    }

    /**
     * 发布回复
     *
     * @param replyPublishVO 回复发布的内容，包括目标回答ID、回复内容
     * @param token          header中的jwt
     * @return 是否成功
     */
    @PostMapping("/publishReply")
    public RestResponse publishReply(@RequestBody ReplyPublishVO replyPublishVO,
                                     @RequestHeader("Authorization") String token) {
        if (replyPublishVO == null) {
            return RestResponse.fail(1, "缺少参数");
        }
        if (StrUtil.isBlank(replyPublishVO.getText()) || replyPublishVO.getAnswerId() == null) {
            return RestResponse.fail(2, "存在为空参数");
        }
        //能到达这里，token已经是被校验过得了，我们只获取当前用户ID
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //发布
        if (!questionReplyService.publish(userId, replyPublishVO)) {
            return RestResponse.fail(3, "发布失败，服务器内部错误");
        } else {
            return RestResponse.ok(null, null);
        }
    }

}
