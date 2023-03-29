package soda.npe.servicequestion.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import soda.npe.common.constant.DBConstant;
import soda.npe.common.entity.QuestionReply;
import soda.npe.common.mapper.QuestionReplyMapper;

import java.util.List;

@Service
public class QuestionReplyService extends ServiceImpl<QuestionReplyMapper, QuestionReply> {

    public List<QuestionReply> getOfAnswer(Long answerId, Integer page) {
        return this.list(new LambdaQueryWrapper<QuestionReply>()
                .eq(QuestionReply::getGoalAnswerId, answerId)
                .orderByDesc(QuestionReply::getPublishTime)
                .last("limit " + (DBConstant.PAGE_SIZE * (page - 1)) + "," + DBConstant.PAGE_SIZE));
    }

}
