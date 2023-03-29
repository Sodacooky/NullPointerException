package soda.npe.servicequestion.service;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import soda.npe.common.entity.QuestionAnswer;
import soda.npe.common.entity.QuestionInfo;
import soda.npe.common.mapper.QuestionAnswerMapper;
import soda.npe.common.mapper.QuestionInfoMapper;

import java.util.List;

@Service
public class SearchService {

    @Resource
    private QuestionAnswerService questionAnswerService;

    @Resource
    private QuestionInfoService questionInfoService;

    @Resource
    private QuestionInfoMapper questionInfoMapper;

    @Resource
    private QuestionAnswerMapper questionAnswerMapper;

    public List<QuestionInfo> doQuestionInfoSearch(String keyword, Integer page, String order) {
        return null;

    }

    public List<QuestionAnswer> doAnswerSearch(String keyword, Integer page, String order) {
        return null;
   
    }

}
