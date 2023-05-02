package soda.npe.servicearticle.controller;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.common.controller.Response;
import soda.npe.common.entity.Article;
import soda.npe.servicearticle.service.ApprovalArticleService;
import soda.npe.servicearticle.service.ArticleReplyService;
import soda.npe.servicearticle.service.ArticleService;

/**
 * 处理文章相关的内容读取控制器
 */
@RestController
@RequestMapping("/public")
public class DataFetchController {

    @Resource
    private ArticleService articleService;
    @Resource
    private ArticleReplyService articleReplyService;

    @Resource
    private ApprovalArticleService approvalArticleService;

    /**
     * 以从新到就的顺序，获取指定用户发表过的文章
     *
     * @param userId 用户ID
     * @param page   页
     * @return Article对象文章列表，该接口会将正文去掉以保证速度
     */
    @GetMapping("/getArticlePublishedBy")
    public Response getArticlePublishedBy(Long userId, Integer page) {
        if (userId == null) return Response.fail(1, "未指定用户");
        if (page == null || page < 1) page = 1;
        return Response.ok(null, articleService.getPublishedBy(userId, page));
    }


    /**
     * 获取文正整个内容
     *
     * @param articleId 文章ID
     * @return Article对象
     */
    @GetMapping("/getArticle")
    public Response getArticle(Long articleId) {
        if (articleId == null) return Response.fail(1, "articleId is null");
        Article one = articleService.getById(articleId);
        if (one == null) return Response.fail(1, "指定ID没有找到对象");
        return Response.ok(null, one);
    }

    /**
     * 获取文章的回复
     *
     * @param articleId 文章ID
     * @param page      页
     * @return 回复列表
     */
    @GetMapping("/getReplyOf")
    public Response getReplyOfAnswer(Long articleId, Integer page) {
        if (articleId == null) return Response.fail(1, "未指定回答");
        if (page == null || page < 1) page = 1;
        return Response.ok(null, articleReplyService.getOf(articleId, page));
    }

    /**
     * 获取文章回复数量
     *
     * @param articleId 文章ID
     * @return 回复数量
     */
    @GetMapping("/getReplyAmountOf")
    public Response getReplyAmountOf(Long articleId) {
        if (articleId == null) return Response.fail(1, "questionId is null");
        return Response.ok(null, articleReplyService.getReplyAmountOf(articleId));
    }

    /**
     * 获取文章点赞数量
     *
     * @param articleId 文章ID
     * @return 点赞数量
     */
    @GetMapping("/getApprovalAmountOf")
    public Response getApprovalAmountOf(Long articleId) {
        if (articleId == null) return Response.fail(1, "questionId is null");
        return Response.ok(null, approvalArticleService.getApprovalAmountOf(articleId));
    }

    /**
     * 用于在发布文章时，提供一个类型推荐
     *
     * @param input 输入
     * @return 0~8个推荐类型
     */
    @GetMapping("/getCategoriesSuggestion")
    public Response getCategoriesSuggestion(String input) {
        if (StrUtil.isBlank(input)) return Response.fail(1, "未输入");
        return Response.ok(articleService.getCategoriesSuggestion(input));
    }

}
