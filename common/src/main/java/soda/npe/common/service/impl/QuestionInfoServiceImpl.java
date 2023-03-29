package soda.npe.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import soda.npe.common.entity.QuestionInfo;
import soda.npe.common.service.QuestionInfoService;
import soda.npe.common.mapper.QuestionInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author sodac
* @description 针对表【question_info(问题的信息)】的数据库操作Service实现
* @createDate 2023-03-28 23:16:19
*/
@Service
public class QuestionInfoServiceImpl extends ServiceImpl<QuestionInfoMapper, QuestionInfo>
    implements QuestionInfoService{

}




