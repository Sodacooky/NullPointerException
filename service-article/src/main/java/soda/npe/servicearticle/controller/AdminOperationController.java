package soda.npe.servicearticle.controller;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import soda.npe.common.controller.Response;
import soda.npe.servicearticle.service.ArticleReplyService;
import soda.npe.servicearticle.service.ArticleService;
import soda.npe.servicearticle.vo.ModifyArticleVO;
import soda.npe.servicearticle.vo.ModifyReplyVO;

@RestController
@RequestMapping("/auth/admin")
public class AdminOperationController {

    @Resource
    private ArticleService articleService;

    @Resource
    private ArticleReplyService articleReplyService;

    @PostMapping("/modifyArticle")
    public Response modifyArticle(@RequestBody ModifyArticleVO vo) {
        //判断问题ID存在
        if (vo.getId() == null || articleService.getById(vo.getId()) == null) {
            return Response.fail(1, "回答ID未指定或不存在");
        }
        //判断内容空白
        if (StrUtil.hasBlank(vo.getTitle(), vo.getText(), vo.getCategory())) {
            return Response.fail(3, "所有字段不能为空");
        }
        //传入修改
        if (articleService.updateQuestionInfo(vo)) return Response.ok();
        else return Response.fail(5, "内部修改失败");
    }

    @PostMapping("/modifyReply")
    public Response modifyReply(@RequestBody ModifyReplyVO vo) {
        //判断问题ID存在
        if (vo.getId() == null || articleService.getById(vo.getId()) == null) {
            return Response.fail(1, "回复ID未指定或不存在");
        }
        //判断内容空白
        if (StrUtil.isBlank(vo.getText())) {
            return Response.fail(3, "目标文本不能为空");
        }
        //传入修改
        if (articleReplyService.updateReply(vo.getId(), vo.getText())) return Response.ok();
        else return Response.fail(5, "内部修改失败");
    }

    @GetMapping("/removeArticle")
    public Response removeArticle(Long articleId) {
        //判断问题ID存在
        if (articleId == null || articleService.getById(articleId) == null) {
            return Response.fail(1, "文章ID未指定或不存在");
        }
        //执行删除
        if (articleService.removeWithReply(articleId)) return Response.ok();
        else return Response.fail(3, "内部修改失败");
    }

    @GetMapping("/removeReply")
    public Response removeReply(Long replyId) {
        //判断回答ID存在
        if (replyId == null || articleReplyService.getById(replyId) == null) {
            return Response.fail(1, "回答ID未指定或不存在");
        }
        //执行删除
        if (articleReplyService.removeAndNotice(replyId)) return Response.ok();
        else return Response.fail(3, "内部修改失败");
    }

}
