package soda.npe.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import soda.npe.common.entity.QuestionAnswer;
import soda.npe.common.service.QuestionAnswerService;
import soda.npe.common.mapper.QuestionAnswerMapper;
import org.springframework.stereotype.Service;

/**
* @author sodac
* @description 针对表【question_answer(对问题的回答，包括一楼的问题详细内容)】的数据库操作Service实现
* @createDate 2023-03-28 23:16:19
*/
@Service
public class QuestionAnswerServiceImpl extends ServiceImpl<QuestionAnswerMapper, QuestionAnswer>
    implements QuestionAnswerService{

}




