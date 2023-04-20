package soda.npe.servicemiscellaneous.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import soda.npe.common.entity.Report;
import soda.npe.common.mapper.ReportMapper;

import java.util.Date;

@Service
public class ReportService extends ServiceImpl<ReportMapper, Report> {

    public boolean isReported(Long goalId, Long reporterUserId, ReportType type) {
        return switch (type) {
            case REPORT_QUESTION -> this.getOne(new LambdaQueryWrapper<Report>()
                    .eq(Report::getReporterId, reporterUserId)
                    .eq(Report::getGoalQuestionId, goalId)) != null;
            case REPORT_ANSWER -> this.getOne(new LambdaQueryWrapper<Report>()
                    .eq(Report::getReporterId, reporterUserId)
                    .eq(Report::getGoalQuestionAnswerId, goalId)) != null;
            case REPORT_ARTICLE -> this.getOne(new LambdaQueryWrapper<Report>()
                    .eq(Report::getReporterId, reporterUserId)
                    .eq(Report::getGoalArticleId, goalId)) != null;
            case REPORT_ARTICLE_REPLY -> this.getOne(new LambdaQueryWrapper<Report>()
                    .eq(Report::getReporterId, reporterUserId)
                    .eq(Report::getGoalArticleReplyId, goalId)) != null;
            case REPORT_USER -> this.getOne(new LambdaQueryWrapper<Report>()
                    .eq(Report::getReporterId, reporterUserId)
                    .eq(Report::getGoalUserId, goalId)) != null;
        };
    }

    public boolean doReport(Long goalId, Long reporterUserId, String comment, ReportType type) {
        //虽然已经默认经过Controller的检查，这里还是再检查一次是否举报过
        if (isReported(goalId, reporterUserId, type)) return false;
        //否则添加举报
        Report report = new Report();
        report.setTime(new Date());
        report.setComment(comment);
        report.setIsProcessed(0);
        report.setReporterId(reporterUserId);
        switch (type) {
            case REPORT_ANSWER -> report.setGoalQuestionAnswerId(goalId);
            case REPORT_ARTICLE -> report.setGoalArticleId(goalId);
            case REPORT_ARTICLE_REPLY -> report.setGoalArticleReplyId(goalId);
            case REPORT_QUESTION -> report.setGoalQuestionId(goalId);
            case REPORT_USER -> report.setGoalUserId(goalId);
        }
        return this.save(report);
    }

    public enum ReportType {
        REPORT_ANSWER, REPORT_ARTICLE, REPORT_ARTICLE_REPLY, REPORT_QUESTION, REPORT_USER
    }
}
