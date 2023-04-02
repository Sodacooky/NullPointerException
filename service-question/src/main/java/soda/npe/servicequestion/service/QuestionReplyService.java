package soda.npe.servicequestion.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import soda.npe.common.constant.DBConstant;
import soda.npe.common.entity.QuestionReply;
import soda.npe.common.mapper.QuestionReplyMapper;
import soda.npe.servicequestion.vo.ReplyPublishVO;

import java.util.Date;
import java.util.List;

@Service
public class QuestionReplyService extends ServiceImpl<QuestionReplyMapper, QuestionReply> {

    public List<QuestionReply> getOfAnswer(Long answerId, Integer page) {
        return this.list(new LambdaQueryWrapper<QuestionReply>()
                .eq(QuestionReply::getGoalAnswerId, answerId)
                .orderByDesc(QuestionReply::getPublishTime)
                .last("limit " + (DBConstant.PAGE_SIZE * (page - 1)) + "," + DBConstant.PAGE_SIZE));
    }

    public boolean publish(Long userId, ReplyPublishVO replyPublishVO) {
        //构建Reply实体
        QuestionReply questionReply = new QuestionReply();
        questionReply.setPublisherId(userId);
        questionReply.setText(replyPublishVO.getText());
        questionReply.setGoalAnswerId(replyPublishVO.getAnswerId());
        questionReply.setPublishTime(new Date());
        //储存
        return this.save(questionReply);
    }
}
