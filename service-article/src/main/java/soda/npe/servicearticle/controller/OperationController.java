package soda.npe.servicearticle.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.common.controller.RestResponse;
import soda.npe.common.utils.JwtAuthUtil;
import soda.npe.servicequestion.service.ApprovalAnswerService;
import soda.npe.servicequestion.service.UserQuestionSubscriptionService;


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

    @GetMapping("/approve")
    public RestResponse approve(Long answerId, @RequestHeader("Authorization") String token) {
        if (answerId == null) return RestResponse.fail(1, "未指定回答");
        //能到达这里，token已经是被校验过得了，我们只获取当前用户ID
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //做
        if (approvalAnswerService.approve(answerId, userId)) return RestResponse.ok(null, null);
        else return RestResponse.fail(2, "可能已点赞或答案不存在");
    }

    @GetMapping("/unApprove")
    public RestResponse unApprove(Long answerId, @RequestHeader("Authorization") String token) {
        if (answerId == null) return RestResponse.fail(1, "未指定回答");
        //能到达这里，token已经是被校验过得了，我们只获取当前用户ID
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //做
        if (approvalAnswerService.unApprove(answerId, userId)) return RestResponse.ok(null, null);
        else return RestResponse.fail(2, "可能未点赞或答案不存在");
    }

}
