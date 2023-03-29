package soda.npe.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import soda.npe.common.entity.ApprovalAnswer;
import soda.npe.common.service.ApprovalAnswerService;
import soda.npe.common.mapper.ApprovalAnswerMapper;
import org.springframework.stereotype.Service;

/**
* @author sodac
* @description 针对表【approval_answer(用户对回答赞的记录)】的数据库操作Service实现
* @createDate 2023-03-28 23:16:19
*/
@Service
public class ApprovalAnswerServiceImpl extends ServiceImpl<ApprovalAnswerMapper, ApprovalAnswer>
    implements ApprovalAnswerService{

}




