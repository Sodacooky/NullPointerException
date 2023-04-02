package soda.npe.servicearticle.controller;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import soda.npe.common.controller.RestResponse;
import soda.npe.common.utils.JwtAuthUtil;
import soda.npe.servicearticle.service.ApprovalArticleService;
import soda.npe.servicearticle.service.ArticleReplyService;
import soda.npe.servicearticle.service.ArticleService;
import soda.npe.servicearticle.vo.ArticlePublishVO;
import soda.npe.servicearticle.vo.ReplyPublishVO;


/**
 * 对问题和答案发布、修改、点赞订阅等操作
 */
@RestController
@RequestMapping("/operation")
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
    public RestResponse approve(Long articleId, @RequestHeader("Authorization") String token) {
        if (articleId == null) return RestResponse.fail(1, "未指定回答");
        //能到达这里，token已经是被校验过得了，我们只获取当前用户ID
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //做
        if (approvalArticleService.approve(articleId, userId)) return RestResponse.ok(null, null);
        else return RestResponse.fail(2, "可能已点赞或文章不存在");
    }

    @GetMapping("/unApprove")
    public RestResponse unApprove(Long articleId, @RequestHeader("Authorization") String token) {
        if (articleId == null) return RestResponse.fail(1, "未指定回答");
        //能到达这里，token已经是被校验过得了，我们只获取当前用户ID
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //做
        if (approvalArticleService.unApprove(articleId, userId)) return RestResponse.ok(null, null);
        else return RestResponse.fail(2, "可能未点赞或文章不存在");
    }

    @PostMapping("/publishArticle")
    public RestResponse publishArticle(@RequestBody ArticlePublishVO articlePublishVO,
                                       @RequestHeader("Authorization") String token) {
        if (articlePublishVO == null) {
            return RestResponse.fail(1, "缺少参数");
        }
        if (StrUtil.hasBlank(articlePublishVO.getTitle(), articlePublishVO.getText(), articlePublishVO.getCategory())) {
            return RestResponse.fail(2, "存在为空参数");
        }
        //能到达这里，token已经是被校验过得了，我们只获取当前用户ID
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //发布
        if (!articleService.publish(userId, articlePublishVO)) {
            return RestResponse.fail(3, "发布失败，服务器内部错误");
        } else {
            return RestResponse.ok(null, null);
        }
    }

    @PostMapping("/publishReply")
    public RestResponse publishReply(@RequestBody ReplyPublishVO replyPublishVO,
                                     @RequestHeader("Authorization") String token) {
        if (replyPublishVO == null) {
            return RestResponse.fail(1, "缺少参数");
        }
        if (StrUtil.isBlank(replyPublishVO.getText()) || replyPublishVO.getArticleId() == null) {
            return RestResponse.fail(2, "存在为空参数");
        }
        //能到达这里，token已经是被校验过得了，我们只获取当前用户ID
        Long userId = jwtAuthUtil.getPayload(token).getLong("userId");
        //发布
        if (!articleReplyService.publish(userId, replyPublishVO)) {
            return RestResponse.fail(3, "发布失败，服务器内部错误");
        } else {
            return RestResponse.ok(null, null);
        }
    }

}
