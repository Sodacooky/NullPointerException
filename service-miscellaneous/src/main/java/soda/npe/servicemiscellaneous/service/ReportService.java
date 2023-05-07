package soda.npe.servicemiscellaneous.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import soda.npe.common.constant.DBConstant;
import soda.npe.common.entity.*;
import soda.npe.common.mapper.*;
import soda.npe.servicemiscellaneous.vo.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReportService extends ServiceImpl<ReportMapper, Report> {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private QuestionInfoMapper questionInfoMapper;

    @Resource
    private QuestionAnswerMapper questionAnswerMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ArticleReplyMapper articleReplyMapper;


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

    private List<Report> fetchReports(ReportType type, Integer page, Boolean isAsc, Boolean isShowProcessed) {
        LambdaQueryWrapper<Report> wrapper = new LambdaQueryWrapper<>();
        switch (type) {
            case REPORT_ANSWER -> wrapper.isNotNull(Report::getGoalQuestionAnswerId);
            case REPORT_ARTICLE -> wrapper.isNotNull(Report::getGoalArticleId);
            case REPORT_ARTICLE_REPLY -> wrapper.isNotNull(Report::getGoalArticleReplyId);
            case REPORT_QUESTION -> wrapper.isNotNull(Report::getGoalQuestionId);
            case REPORT_USER -> wrapper.isNotNull(Report::getGoalUserId);
        }
        wrapper.eq(!isShowProcessed, Report::getIsProcessed, 0)
                .orderBy(true, isAsc, Report::getTime)
                .last("limit " + (DBConstant.PAGE_SIZE * (page - 1)) + "," + DBConstant.PAGE_SIZE);
        return this.list(wrapper);
    }

    public List<QuestionReportPreviewVO> getQuestionReport(Integer page, Boolean isAsc, Boolean isShowProcessed) {
        List<Report> reports = fetchReports(ReportType.REPORT_QUESTION, page, isAsc, isShowProcessed);
        //convert to vo
        List<QuestionReportPreviewVO> result = new ArrayList<>(reports.size());
        for (var report : reports) {
            QuestionReportPreviewVO vo = new QuestionReportPreviewVO();
            vo.setId(report.getId());
            vo.setGoalQuestionId(report.getGoalQuestionId());
            vo.setTime(report.getTime());
            vo.setIsProcessed(report.getIsProcessed());
            //fill question info
            QuestionInfo questionInfo = questionInfoMapper.selectById(vo.getGoalQuestionId());
            vo.setGoalQuestionTitle(questionInfo.getTitle());
            //fill short text
            String text = questionAnswerMapper.selectOne(
                    new LambdaQueryWrapper<QuestionAnswer>()
                            .eq(QuestionAnswer::getOrderNumber, 0)
                            .eq(QuestionAnswer::getQuestionId, questionInfo.getId())).getText();
            vo.setGoalQuestionShortText(text.length() > 64 ? text.substring(0, 64) + "..." : text);
            //fill user info
            UserInfo userInfo = userInfoMapper.selectById(questionInfo.getPublisherId());
            vo.setGoalOwnerNickname(userInfo.getNickname());
            vo.setGoalOwnerAvatar(userInfo.getAvatar());
            //
            result.add(vo);
        }
        return result;
    }

    public List<AnswerReportPreviewVO> getAnswerReport(Integer page, Boolean isAsc, Boolean isShowProcessed) {
        //fetch reports
        List<Report> reports = fetchReports(ReportType.REPORT_ANSWER, page, isAsc, isShowProcessed);
        //convert to vo
        List<AnswerReportPreviewVO> result = new ArrayList<>(reports.size());
        for (var report : reports) {
            AnswerReportPreviewVO vo = new AnswerReportPreviewVO();
            vo.setId(report.getId());
            vo.setGoalQuestionAnswerId(report.getGoalQuestionAnswerId());
            vo.setTime(report.getTime());
            vo.setIsProcessed(report.getIsProcessed());
            //fill answer short text
            QuestionAnswer answer = questionAnswerMapper.selectById(vo.getGoalQuestionAnswerId());
            vo.setGoalQuestionAnswerShortText(
                    answer.getText().length() > 64 ? answer.getText().substring(0, 64) + "..." : answer.getText());
            //fill question info
            QuestionInfo questionInfo = questionInfoMapper.selectById(answer.getQuestionId());
            vo.setGoalQuestionTitle(questionInfo.getTitle());
            //fill user info
            UserInfo userInfo = userInfoMapper.selectById(answer.getPublisherId());
            vo.setGoalOwnerNickname(userInfo.getNickname());
            vo.setGoalOwnerAvatar(userInfo.getAvatar());
            //
            result.add(vo);
        }
        return result;
    }

    public List<ArticleReportPreviewVO> getArticleReport(Integer page, Boolean isAsc, Boolean isShowProcessed) {
        //fetch reports
        List<Report> reports = fetchReports(ReportType.REPORT_ARTICLE, page, isAsc, isShowProcessed);
        //convert to vo
        List<ArticleReportPreviewVO> result = new ArrayList<>(reports.size());
        for (var report : reports) {
            ArticleReportPreviewVO vo = new ArticleReportPreviewVO();
            vo.setId(report.getId());
            vo.setGoalArticleId(report.getGoalArticleId());
            vo.setTime(report.getTime());
            vo.setIsProcessed(report.getIsProcessed());
            //fill article info
            Article article = articleMapper.selectById(vo.getGoalArticleId());
            vo.setGoalArticleShortText(
                    article.getText().length() > 64 ? article.getText().substring(0, 64) + "..." : article.getText());
            vo.setGoalArticleTitle(article.getTitle());
            //fill user info
            UserInfo userInfo = userInfoMapper.selectById(article.getPublisherId());
            vo.setGoalOwnerNickname(userInfo.getNickname());
            vo.setGoalOwnerAvatar(userInfo.getAvatar());
            //
            result.add(vo);
        }
        return result;
    }

    public List<ReplyReportPreviewVO> getArticleReplyReport(Integer page, Boolean isAsc, Boolean isShowProcessed) {
        //fetch reports
        List<Report> reports = fetchReports(ReportType.REPORT_ARTICLE_REPLY, page, isAsc, isShowProcessed);
        //convert to vo
        List<ReplyReportPreviewVO> result = new ArrayList<>(reports.size());
        for (var report : reports) {
            ReplyReportPreviewVO vo = new ReplyReportPreviewVO();
            vo.setId(report.getId());
            vo.setGoalArticleReplyId(report.getGoalArticleReplyId());
            vo.setTime(report.getTime());
            vo.setIsProcessed(report.getIsProcessed());
            //fill reply short text
            ArticleReply reply = articleReplyMapper.selectById(vo.getGoalArticleReplyId());
            vo.setGoalArticleReplyShortText(
                    reply.getText().length() > 64 ? reply.getText().substring(0, 64) + "..." : reply.getText());
            //fill article info
            Article article = articleMapper.selectById(reply.getGoalArticleId());
            vo.setGoalArticleTitle(article.getTitle());
            //fill user info
            UserInfo userInfo = userInfoMapper.selectById(reply.getPublisherId());
            vo.setGoalOwnerNickname(userInfo.getNickname());
            vo.setGoalOwnerAvatar(userInfo.getAvatar());
            //
            result.add(vo);
        }
        return result;
    }

    public List<UserReportPreviewVO> getUserReport(Integer page, Boolean isAsc, Boolean isShowProcessed) {
        //fetch reports
        List<Report> reports = fetchReports(ReportType.REPORT_USER, page, isAsc, isShowProcessed);
        //convert
        List<UserReportPreviewVO> result = new ArrayList<>(reports.size());
        for (var report : reports) {
            UserReportPreviewVO vo = new UserReportPreviewVO();
            vo.setId(report.getId());
            vo.setGoalUserId(report.getGoalUserId());
            vo.setTime(report.getTime());
            vo.setIsProcessed(report.getIsProcessed());
            //fill user info
            UserInfo userInfo = userInfoMapper.selectById(vo.getGoalUserId());
            vo.setGoalUserNickname(userInfo.getNickname());
            vo.setGoalUserDescription(userInfo.getDescription());
            vo.setGoalUserAvatar(userInfo.getAvatar());
            //
            result.add(vo);
        }
        return result;
    }

    public enum ReportType {
        REPORT_ANSWER, REPORT_ARTICLE, REPORT_ARTICLE_REPLY, REPORT_QUESTION, REPORT_USER
    }


}
