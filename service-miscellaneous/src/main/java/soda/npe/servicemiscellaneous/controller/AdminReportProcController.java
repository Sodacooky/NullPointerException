package soda.npe.servicemiscellaneous.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.common.controller.Response;
import soda.npe.servicemiscellaneous.service.ReportService;

@RestController
@RequestMapping("/auth/admin")
public class AdminReportProcController {

    @Resource
    private ReportService reportService;


    @GetMapping("/getQuestionReport")
    public Response getQuestionReport(Integer page, Boolean isAsc, Boolean isShowProcessed) {
        if (page == null || page < 1) page = 1;
        if (isAsc == null) isAsc = false;
        if (isShowProcessed == null) isShowProcessed = false;
        return Response.ok(reportService.getQuestionReport(page, isAsc, isShowProcessed));
    }

    @GetMapping("/getAnswerReport")
    public Response getAnswerReport(Integer page, Boolean isAsc, Boolean isShowProcessed) {
        if (page == null || page < 1) page = 1;
        if (isAsc == null) isAsc = false;
        if (isShowProcessed == null) isShowProcessed = false;
        return Response.ok(reportService.getAnswerReport(page, isAsc, isShowProcessed));
    }

    @GetMapping("/getArticleReport")
    public Response getArticleReport(Integer page, Boolean isAsc, Boolean isShowProcessed) {
        if (page == null || page < 1) page = 1;
        if (isAsc == null) isAsc = false;
        if (isShowProcessed == null) isShowProcessed = false;
        return Response.ok(reportService.getArticleReport(page, isAsc, isShowProcessed));
    }

    @GetMapping("/getArticleReplyReport")
    public Response getArticleReplyReport(Integer page, Boolean isAsc, Boolean isShowProcessed) {
        if (page == null || page < 1) page = 1;
        if (isAsc == null) isAsc = false;
        if (isShowProcessed == null) isShowProcessed = false;
        return Response.ok(reportService.getArticleReplyReport(page, isAsc, isShowProcessed));
    }

    @GetMapping("/getUserReport")
    public Response getUserReport(Integer page, Boolean isAsc, Boolean isShowProcessed) {
        if (page == null || page < 1) page = 1;
        if (isAsc == null) isAsc = false;
        if (isShowProcessed == null) isShowProcessed = false;
        return Response.ok(reportService.getUserReport(page, isAsc, isShowProcessed));
    }


}
