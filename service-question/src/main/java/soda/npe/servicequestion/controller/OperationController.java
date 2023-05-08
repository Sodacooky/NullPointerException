package soda.npe.servicequestion.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import soda.npe.common.controller.Response;
import soda.npe.common.entity.ApprovalAnswer;
import soda.npe.common.entity.UserQuestionSubscription;
import soda.npe.common.utils.JwtAuthUtil;
import soda.npe.servicequestion.service.ApprovalAnswerService;
import soda.npe.servicequestion.service.QuestionAnswerService;
import soda.npe.servicequestion.service.QuestionInfoService;
import soda.npe.servicequestion.service.UserQuestionSubscriptionService;
import soda.npe.servicequestion.vo.AnswerPublishVO;
import soda.npe.servicequestion.vo.QuestionPublishVO;


/**
 * 对问题和答案发布、修改、点赞订阅等操作
 */
@RestController
@RequestMapping("/auth")
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

    /**
     * 用户订阅问题，从Header中获取JWT并识别当前用户
     *
     * @param questionId 要订阅的问题ID
     * @param token      Header中的JWT
     * @return 是否成功
     */
    @GetMapping("/subscription")
    public Response subscription(Long questionId, @RequestHeader("Authorization") String token) {
        if (questionId == null) return Response.fail(1, "未指定问题");
        //能到达这里，token已经是被校验过得了，我们只获取当前用户ID
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //做
        if (userQuestionSubscriptionService.subscription(questionId, userId)) return Response.ok(null, null);
        else return Response.fail(2, "可能已经订阅或者问题不存在");
    }


    /**
     * 用户取消订阅问题，从Header中获取JWT并识别当前用户
     *
     * @param questionId 要订阅的问题ID
     * @param token      Header中的JWT
     * @return 是否成功
     */
    @GetMapping("/unSubscription")
    public Response unSubscription(Long questionId, @RequestHeader("Authorization") String token) {
        if (questionId == null) return Response.fail(1, "未指定问题");
        //能到达这里，token已经是被校验过得了，我们只获取当前用户ID
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //做
        if (userQuestionSubscriptionService.unSubscription(questionId, userId)) return Response.ok(null, null);
        else return Response.fail(2, "可能未订阅或者问题不存在");
    }

    @GetMapping("/isSubscribed")
    public Response isSubscribed(Long questionId, @RequestHeader("Authorization") String token) {
        if (questionId == null) return Response.fail(1, "未指定回答");
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //check record exist
        UserQuestionSubscription record = userQuestionSubscriptionService.getOne(
                new LambdaQueryWrapper<UserQuestionSubscription>()
                        .eq(UserQuestionSubscription::getQuestionId, questionId)
                        .eq(UserQuestionSubscription::getUserId, userId));
        //
        return Response.ok(record != null);
    }

    /**
     * 点赞回答
     *
     * @param answerId 回答ID
     * @param token    Header中的JWT
     * @return 是否成功
     */
    @GetMapping("/approve")
    public Response approve(Long answerId, @RequestHeader("Authorization") String token) {
        if (answerId == null) return Response.fail(1, "未指定回答");
        //能到达这里，token已经是被校验过得了，我们只获取当前用户ID
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //做
        if (approvalAnswerService.approve(answerId, userId)) return Response.ok(null, null);
        else return Response.fail(2, "可能已点赞或答案不存在");
    }

    /**
     * 取消点赞回答
     *
     * @param answerId 回答ID
     * @param token    Header中的JWT
     * @return 是否成功
     */
    @GetMapping("/unApprove")
    public Response unApprove(Long answerId, @RequestHeader("Authorization") String token) {
        if (answerId == null) return Response.fail(1, "未指定回答");
        //能到达这里，token已经是被校验过得了，我们只获取当前用户ID
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //做
        if (approvalAnswerService.unApprove(answerId, userId)) return Response.ok(null, null);
        else return Response.fail(2, "可能未点赞或答案不存在");
    }

    @GetMapping("/isApproved")
    public Response isApproved(Long answerId, @RequestHeader("Authorization") String token) {
        if (answerId == null) return Response.fail(1, "未指定回答");
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //check record exist
        ApprovalAnswer record = approvalAnswerService.getOne(
                new LambdaQueryWrapper<ApprovalAnswer>()
                        .eq(ApprovalAnswer::getAnswerId, answerId)
                        .eq(ApprovalAnswer::getUserId, userId));
        //
        return Response.ok(record != null);
    }

    /**
     * 问题的发布
     *
     * @param questionPublishVO 问题发布的内容，包括标题、分类、正文
     * @param token             Header中的JWT
     * @return 是否成功，成功data中附带刚刚发布问题的ID
     */
    @PostMapping("/publishQuestion")
    public Response publishQuestion(@RequestBody QuestionPublishVO questionPublishVO,
                                    @RequestHeader("Authorization") String token) {
        if (questionPublishVO == null) {
            return Response.fail(1, "缺少参数");
        }
        if (StrUtil.hasBlank(questionPublishVO.getText(), questionPublishVO.getTitle(), questionPublishVO.getCategory())) {
            return Response.fail(2, "存在为空参数");
        }
        //能到达这里，token已经是被校验过得了，我们只获取当前用户ID
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //发布
        Long publishInfoId = questionInfoService.publish(userId, questionPublishVO);
        if (publishInfoId == null) {
            return Response.fail(3, "发布失败，服务器内部错误");
        } else {
            return Response.ok(null, publishInfoId);
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
    public Response publishAnswer(@RequestBody AnswerPublishVO answerPublishVO,
                                  @RequestHeader("Authorization") String token) {
        if (answerPublishVO == null) {
            return Response.fail(1, "缺少参数");
        }
        if (StrUtil.isBlank(answerPublishVO.getText()) || answerPublishVO.getQuestionId() == null) {
            return Response.fail(2, "存在为空参数");
        }
        //能到达这里，token已经是被校验过得了，我们只获取当前用户ID
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //发布
        if (!questionAnswerService.publish(userId, answerPublishVO)) {
            return Response.fail(3, "发布失败，服务器内部错误");
        } else {
            return Response.ok(null, null);
        }
    }

}
