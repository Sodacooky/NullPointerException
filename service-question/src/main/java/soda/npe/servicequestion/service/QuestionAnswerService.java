package soda.npe.servicequestion.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import soda.npe.common.constant.DBConstant;
import soda.npe.common.entity.QuestionAnswer;
import soda.npe.common.entity.QuestionInfo;
import soda.npe.common.mapper.QuestionAnswerMapper;

import java.util.List;

@Service
public class QuestionAnswerService extends ServiceImpl<QuestionAnswerMapper, QuestionAnswer> {

    public List<QuestionAnswer> getAnswerPublishedBy(long userId,int page){
        return this.list(
                new LambdaQueryWrapper<QuestionAnswer>()
                        .eq(QuestionAnswer::getPublisherId, userId)
                        .orderByDesc(QuestionAnswer::getPublishTime)
                        .last("limit " + (DBConstant.PAGE_SIZE * (page - 1)) + "," + DBConstant.PAGE_SIZE));
    }

    public List<QuestionAnswer> getOfQuestionByTime(Long questionId, Integer page, Boolean isAsc){
        return this.list(
                new LambdaQueryWrapper<QuestionAnswer>()
                        .eq(QuestionAnswer::getQuestionId,questionId)
                        .ge(QuestionAnswer::getOrderNumber,1)
                        .orderBy(true,isAsc,QuestionAnswer::getPublishTime)
                        .last("limit " + (DBConstant.PAGE_SIZE * (page - 1)) + "," + DBConstant.PAGE_SIZE));
    }

    public List<QuestionAnswer> getOfQuestionByApproval(Long questionId, Integer page, Boolean isAsc){
        return this.getBaseMapper().getOfQuestionByApproval(questionId,page,DBConstant.PAGE_SIZE,isAsc);
    }

    public List<QuestionAnswer> searchAnswerByTime(String keyword,Integer page,Boolean isAsc){
        return this.list(
                new LambdaQueryWrapper<QuestionAnswer>()
                        .like(QuestionAnswer::getText,keyword)
                        .orderByDesc(QuestionAnswer::getPublishTime)
                        .last("limit " + (DBConstant.PAGE_SIZE * (page - 1)) + "," + DBConstant.PAGE_SIZE));
    }

    public List<QuestionAnswer> searchAnswerByApproval(String keyword,Integer page,Boolean isAsc){
        return this.getBaseMapper().searchAnswerByApproval(keyword, page, isAsc);
    }

}
