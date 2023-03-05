package soda.npe.serviceuser.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import soda.npe.serviceuser.entity.UserQuestionSubscription;
import soda.npe.serviceuser.service.UserQuestionSubscriptionService;
import soda.npe.serviceuser.mapper.UserQuestionSubscriptionMapper;
import org.springframework.stereotype.Service;

/**
* @author sodac
* @description 针对表【user_question_subscription(用户关注的问题)】的数据库操作Service实现
* @createDate 2023-03-05 21:21:28
*/
@Service
public class UserQuestionSubscriptionServiceImpl extends ServiceImpl<UserQuestionSubscriptionMapper, UserQuestionSubscription>
    implements UserQuestionSubscriptionService{

}




