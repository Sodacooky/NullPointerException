package soda.npe.servicearticle.controller;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import soda.npe.common.controller.Response;
import soda.npe.common.utils.JwtAuthUtil;
import soda.npe.servicearticle.service.ApprovalArticleService;
import soda.npe.servicearticle.service.ArticleReplyService;
import soda.npe.servicearticle.service.ArticleService;
import soda.npe.servicearticle.vo.DoArticlePublishVO;
import soda.npe.servicearticle.vo.DoReplyPublishVO;


/**
 * 对文章的操作
 */
@RestController
@RequestMapping("/auth")
public class OperationController {

    @Resource
    private JwtAuthUtil jwtAuthUtil;

    @Resource
    private ApprovalArticleService approvalArticleService;

    @Resource
    private ArticleService articleService;

    @Resource
    private ArticleReplyService articleReplyService;

    @GetMapping("/approve")
    public Response approve(Long articleId, @RequestHeader("Authorization") String token) {
        if (articleId == null) return Response.fail(1, "未指定回答");
        //能到达这里，token已经是被校验过得了，我们只获取当前用户ID
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //做
        if (approvalArticleService.approve(articleId, userId)) return Response.ok(null, null);
        else return Response.fail(2, "可能已点赞或文章不存在");
    }

    @GetMapping("/unApprove")
    public Response unApprove(Long articleId, @RequestHeader("Authorization") String token) {
        if (articleId == null) return Response.fail(1, "未指定回答");
        //能到达这里，token已经是被校验过得了，我们只获取当前用户ID
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //做
        if (approvalArticleService.unApprove(articleId, userId)) return Response.ok(null, null);
        else return Response.fail(2, "可能未点赞或文章不存在");
    }

    /**
     * 发布文章，如果成功返回文章的ID
     *
     * @param doArticlePublishVO title,category,text
     * @param token              jwt
     * @return 失败则失败，成功则文章ID
     */
    @PostMapping("/publishArticle")
    public Response publishArticle(@RequestBody DoArticlePublishVO doArticlePublishVO,
                                   @RequestHeader("Authorization") String token) {
        if (doArticlePublishVO == null) {
            return Response.fail(1, "缺少参数");
        }
        if (StrUtil.hasBlank(doArticlePublishVO.getTitle(), doArticlePublishVO.getText(), doArticlePublishVO.getCategory())) {
            return Response.fail(2, "存在为空参数");
        }
        //能到达这里，token已经是被校验过得了，我们只获取当前用户ID
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //发布
        Long articleId = articleService.publish(userId, doArticlePublishVO);
        if (articleId == null) {
            return Response.fail(3, "发布失败，服务器内部错误");
        } else {
            return Response.ok(articleId);
        }
    }

    @PostMapping("/publishReply")
    public Response publishReply(@RequestBody DoReplyPublishVO doReplyPublishVO,
                                 @RequestHeader("Authorization") String token) {
        if (doReplyPublishVO == null) {
            return Response.fail(1, "缺少参数");
        }
        if (StrUtil.isBlank(doReplyPublishVO.getText()) || doReplyPublishVO.getArticleId() == null) {
            return Response.fail(2, "存在为空参数");
        }
        //能到达这里，token已经是被校验过得了，我们只获取当前用户ID
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //发布
        if (!articleReplyService.publish(userId, doReplyPublishVO)) {
            return Response.fail(3, "发布失败，服务器内部错误");
        } else {
            return Response.ok(null, null);
        }
    }

}
