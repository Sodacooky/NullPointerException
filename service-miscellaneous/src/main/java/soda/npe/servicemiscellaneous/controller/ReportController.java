package soda.npe.servicemiscellaneous.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.common.controller.Response;
import soda.npe.common.utils.JwtAuthUtil;
import soda.npe.servicemiscellaneous.service.ReportService;


@RestController
@RequestMapping("/report")
public class ReportController {

    @Resource
    private JwtAuthUtil jwtAuthUtil;

    @Resource
    private ReportService reportService;

    @GetMapping("/isReported")
    public Response isReported(Long goalId, String goalType, @RequestHeader("Authorization") String token) {
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        return Response.ok(reportService.isReported(goalId, userId, ReportService.ReportType.valueOf(goalType)));
    }

    @GetMapping("/question")
    public Response reportQuestion(Long questionId, String comment, @RequestHeader("Authorization") String token) {
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        if (reportService.isReported(questionId, userId, ReportService.ReportType.REPORT_QUESTION)) {
            return Response.fail(1, "已经举报过了");
        }
        if (!reportService.doReport(questionId, userId, comment, ReportService.ReportType.REPORT_QUESTION)) {
            return Response.fail(2, "举报出现内部错误");
        }
        return Response.ok(null);
    }

    @GetMapping("/answer")
    public Response reportAnswer(Long answerId, String comment, @RequestHeader("Authorization") String token) {
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        if (reportService.isReported(answerId, userId, ReportService.ReportType.REPORT_ANSWER)) {
            return Response.fail(1, "已经举报过了");
        }
        if (!reportService.doReport(answerId, userId, comment, ReportService.ReportType.REPORT_ANSWER)) {
            return Response.fail(2, "举报出现内部错误");
        }
        return Response.ok(null);
    }

    @GetMapping("/article")
    public Response reportArticle(Long articleId, String comment, @RequestHeader("Authorization") String token) {
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        if (reportService.isReported(articleId, userId, ReportService.ReportType.REPORT_ARTICLE)) {
            return Response.fail(1, "已经举报过了");
        }
        if (!reportService.doReport(articleId, userId, comment, ReportService.ReportType.REPORT_ARTICLE)) {
            return Response.fail(2, "举报出现内部错误");
        }
        return Response.ok(null);
    }

    @GetMapping("/articleReply")
    public Response reportArticleReply(Long articleReplyId, String comment, @RequestHeader("Authorization") String token) {
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        if (reportService.isReported(articleReplyId, userId, ReportService.ReportType.REPORT_ARTICLE_REPLY)) {
            return Response.fail(1, "已经举报过了");
        }
        if (!reportService.doReport(articleReplyId, userId, comment, ReportService.ReportType.REPORT_ARTICLE_REPLY)) {
            return Response.fail(2, "举报出现内部错误");
        }
        return Response.ok(null);
    }

    @GetMapping("/user")
    public Response reportUser(Long userId, String comment, @RequestHeader("Authorization") String token) {
        Long reporterUserId = jwtAuthUtil.getPayload(token).getLong("userId");
        if (reportService.isReported(userId, reporterUserId, ReportService.ReportType.REPORT_USER)) {
            return Response.fail(1, "已经举报过了");
        }
        if (!reportService.doReport(userId, reporterUserId, comment, ReportService.ReportType.REPORT_USER)) {
            return Response.fail(2, "举报出现内部错误");
        }
        return Response.ok(null);
    }

}