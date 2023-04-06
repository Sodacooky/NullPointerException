package soda.npe.servicequestion.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import soda.npe.common.constant.DBConstant;
import soda.npe.common.entity.QuestionInfo;
import soda.npe.common.entity.QuestionReply;
import soda.npe.common.entity.UserInfo;
import soda.npe.common.entity.UserNotice;
import soda.npe.common.mapper.*;
import soda.npe.servicequestion.vo.ReplyPublishVO;

import java.util.Date;
import java.util.List;

@Service
public class QuestionReplyService extends ServiceImpl<QuestionReplyMapper, QuestionReply> {

    @Resource
    private QuestionInfoMapper questionInfoMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserNoticeMapper userNoticeMapper;

    @Resource
    private QuestionAnswerMapper questionAnswerMapper;

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
        if (!this.save(questionReply)) return false;
        //构建消息
        // - 获取用户和问题信息
        UserInfo replyOwner = userInfoMapper.selectById(questionReply.getPublisherId());
        QuestionInfo questionInfo = questionInfoMapper.selectById(questionAnswerMapper.selectById(questionReply.getGoalAnswerId()));
        // - 填充实体
        UserNotice userNotice = new UserNotice();
        userNotice.setTitle("回答 " + questionInfo.getTitle() + " 收到来自 " + replyOwner.getNickname() + " 的回复");
        userNotice.setText(questionReply.getText());
        userNotice.setGoalUserId(questionInfo.getPublisherId());
        userNotice.setTime(new Date());
        userNotice.setType("answer_reply");
        userNotice.setIsRead(0);
        userNotice.setSupplement(questionInfo.getId().toString());
        //储存
        return userNoticeMapper.insert(userNotice) == 1;
    }
}
