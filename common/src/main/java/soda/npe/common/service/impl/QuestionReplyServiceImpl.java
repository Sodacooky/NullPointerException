package soda.npe.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import soda.npe.common.entity.QuestionReply;
import soda.npe.common.service.QuestionReplyService;
import soda.npe.common.mapper.QuestionReplyMapper;
import org.springframework.stereotype.Service;

/**
* @author sodac
* @description 针对表【question_reply(对问题中的回答或其他回复的回复)】的数据库操作Service实现
* @createDate 2023-03-28 23:16:19
*/
@Service
public class QuestionReplyServiceImpl extends ServiceImpl<QuestionReplyMapper, QuestionReply>
    implements QuestionReplyService{

}




