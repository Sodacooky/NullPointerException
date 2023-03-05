package soda.npe.serviceuser.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import soda.npe.serviceuser.entity.ApprovalAnswer;
import soda.npe.serviceuser.service.ApprovalAnswerService;
import soda.npe.serviceuser.mapper.ApprovalAnswerMapper;
import org.springframework.stereotype.Service;

/**
* @author sodac
* @description 针对表【approval_answer(用户对回答赞的记录)】的数据库操作Service实现
* @createDate 2023-03-05 21:21:28
*/
@Service
public class ApprovalAnswerServiceImpl extends ServiceImpl<ApprovalAnswerMapper, ApprovalAnswer>
    implements ApprovalAnswerService{

}




