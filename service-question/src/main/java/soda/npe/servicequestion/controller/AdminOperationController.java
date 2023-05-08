package soda.npe.servicequestion.controller;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import soda.npe.common.controller.Response;
import soda.npe.common.entity.QuestionAnswer;
import soda.npe.servicequestion.service.QuestionAnswerService;
import soda.npe.servicequestion.service.QuestionInfoService;
import soda.npe.servicequestion.vo.ModifyQuestionInfoVO;

@RestController
@RequestMapping("/auth/admin")
public class AdminOperationController {

    @Resource
    private QuestionInfoService questionInfoService;

    @Resource
    private QuestionAnswerService questionAnswerService;


    @PostMapping("/modifyQuestionInfo")
    public Response modifyQuestionInfo(@RequestBody ModifyQuestionInfoVO vo) {
        //判断问题ID存在
        if (vo.getId() == null || questionInfoService.getById(vo.getId()) == null) {
            return Response.fail(1, "问题ID未指定或不存在");
        }
        //判断内容空白
        if (StrUtil.hasBlank(vo.getTitle(), vo.getText(), vo.getCategory())) {
            return Response.fail(3, "所有字段不能为空");
        }
        //传入修改
        if (questionInfoService.adminUpdate(vo)) return Response.ok();
        else return Response.fail(5, "内部修改失败");
    }

    @PostMapping("/modifyAnswer")
    public Response modifyAnswer(@RequestBody QuestionAnswer vo) {
        //判断问题ID存在
        if (vo.getId() == null || questionAnswerService.getById(vo.getId()) == null) {
            return Response.fail(1, "回答ID未指定或不存在");
        }
        //判断内容空白
        if (StrUtil.isBlank(vo.getText())) {
            return Response.fail(3, "目标文本不能为空");
        }
        //传入修改
        if (questionAnswerService.adminUpdate(vo.getId(), vo.getText())) return Response.ok();
        else return Response.fail(5, "内部修改失败");
    }


    @GetMapping("/removeQuestion")
    public Response removeQuestion(Long questionId) {
        //判断问题ID存在
        if (questionId == null || questionInfoService.getById(questionId) == null) {
            return Response.fail(1, "回答ID未指定或不存在");
        }
        //执行删除
        if (questionInfoService.adminRemove(questionId)) return Response.ok();
        else return Response.fail(3, "内部修改失败");
    }

    @GetMapping("/removeAnswer")
    public Response removeAnswer(Long answerId) {
        //判断回答ID存在
        if (answerId == null || questionAnswerService.getById(answerId) == null) {
            return Response.fail(1, "回答ID未指定或不存在");
        }
        //执行删除
        if (questionAnswerService.adminRemove(answerId)) return Response.ok();
        else return Response.fail(3, "内部修改失败");
    }

}
