package soda.npe.serviceuser.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.common.controller.Response;
import soda.npe.common.utils.JwtAuthUtil;
import soda.npe.serviceuser.service.UserNoticeService;

/**
 * 获取消息相关的控制器
 */
@RestController
@RequestMapping("/auth/notice")
public class NoticeController {

    @Resource
    private JwtAuthUtil jwtAuthUtil;

    @Resource
    private UserNoticeService userNoticeService;

    /**
     * 获取问题回答通知
     *
     * @param token jwt
     * @return 通知列表
     */
    @GetMapping("/getQuestionAnswer")
    public Response getQuestionAnswer(@RequestHeader("Authorization") String token) {
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        return Response.ok(userNoticeService.getNotice(userId, "question_answer"));
    }

    /**
     * 获取文章回复通知
     *
     * @param token jwt
     * @return 通知列表
     */
    @GetMapping("/getArticleReply")
    public Response getArticleReply(@RequestHeader("Authorization") String token) {
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        return Response.ok(userNoticeService.getNotice(userId, "article_reply"));
    }

    /**
     * 获取系统通知
     *
     * @param token jwt
     * @return 通知列表
     */
    @GetMapping("/getSystem")
    public Response getSystem(@RequestHeader("Authorization") String token) {
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        return Response.ok(userNoticeService.getNotice(userId, "system"));
    }

    /**
     * 获取未读的消息数量
     *
     * @param token jwt
     * @return 数量
     */
    @GetMapping("/getAmount")
    public Response getAmount(@RequestHeader("Authorization") String token) {
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        return Response.ok(userNoticeService.getNoticeAmount(userId));
    }

    /**
     * 标记消息为已读
     *
     * @param noticeId 消息ID
     * @param token    jwt
     * @return 是否成功，对一条已读的进行标记已读操作视为成功
     */
    @GetMapping("/read")
    public Response read(Long noticeId, @RequestHeader("Authorization") String token) {
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        if (userNoticeService.read(noticeId, userId)) {
            return Response.ok(null);
        } else {
            return Response.fail(1, "消息不存在或者不是该消息的接收者");
        }
    }

}
