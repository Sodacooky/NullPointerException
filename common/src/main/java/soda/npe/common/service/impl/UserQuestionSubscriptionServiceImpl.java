package soda.npe.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import soda.npe.common.entity.UserQuestionSubscription;
import soda.npe.common.service.UserQuestionSubscriptionService;
import soda.npe.common.mapper.UserQuestionSubscriptionMapper;
import org.springframework.stereotype.Service;

/**
* @author sodac
* @description 针对表【user_question_subscription(用户关注的问题)】的数据库操作Service实现
* @createDate 2023-03-28 23:16:19
*/
@Service
public class UserQuestionSubscriptionServiceImpl extends ServiceImpl<UserQuestionSubscriptionMapper, UserQuestionSubscription>
    implements UserQuestionSubscriptionService{

}




