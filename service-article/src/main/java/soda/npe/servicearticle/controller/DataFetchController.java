package soda.npe.servicearticle.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.common.controller.RestResponse;
import soda.npe.common.entity.Article;
import soda.npe.servicearticle.service.ApprovalArticleService;
import soda.npe.servicearticle.service.ArticleReplyService;
import soda.npe.servicearticle.service.ArticleService;

/**
 * 处理文章相关的内容读取控制器
 */
@RestController
//@RequestMapping("/")
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
    public RestResponse getArticlePublishedBy(Long userId, Integer page) {
        if (userId == null) return RestResponse.fail(1, "未指定用户");
        if (page == null || page < 1) page = 1;
        return RestResponse.ok(null, articleService.getPublishedBy(userId, page));
    }


    /**
     * 获取文正整个内容
     *
     * @param articleId 文章ID
     * @return Article对象
     */
    @GetMapping("/getArticle")
    public RestResponse getArticle(Long articleId) {
        if (articleId == null) return RestResponse.fail(1, "articleId is null");
        Article one = articleService.getById(articleId);
        if (one == null) return RestResponse.fail(1, "指定ID没有找到对象");
        return RestResponse.ok(null, one);
    }

    /**
     * 获取文章的回复
     *
     * @param articleId 文章ID
     * @param page     页
     * @return 回复列表
     */
    @GetMapping("/getReplyOf")
    public RestResponse getReplyOfAnswer(Long articleId, Integer page) {
        if (articleId == null) return RestResponse.fail(1, "未指定回答");
        if (page == null || page < 1) page = 1;
        return RestResponse.ok(null, articleReplyService.getOf(articleId, page));
    }

    /**
     * 获取文章回复数量
     *
     * @param articleId 文章ID
     * @return 回复数量
     */
    @GetMapping("/getReplyAmountOf")
    public RestResponse getReplyAmountOf(Long articleId) {
        if (articleId == null) return RestResponse.fail(1, "questionId is null");
        return RestResponse.ok(null, articleReplyService.getReplyAmountOf(articleId));
    }

    /**
     * 获取文章点赞数量
     *
     * @param articleId 文章ID
     * @return 点赞数量
     */
    @GetMapping("/getApprovalAmountOf")
    public RestResponse getApprovalAmountOf(Long articleId) {
        if (articleId == null) return RestResponse.fail(1, "questionId is null");
        return RestResponse.ok(null, approvalArticleService.getApprovalAmountOf(articleId));
    }


}
