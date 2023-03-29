package soda.npe.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import soda.npe.common.entity.ApprovalArticle;
import soda.npe.common.service.ApprovalArticleService;
import soda.npe.common.mapper.ApprovalArticleMapper;
import org.springframework.stereotype.Service;

/**
* @author sodac
* @description 针对表【approval_article(用户对文章赞的记录)】的数据库操作Service实现
* @createDate 2023-03-28 23:16:19
*/
@Service
public class ApprovalArticleServiceImpl extends ServiceImpl<ApprovalArticleMapper, ApprovalArticle>
    implements ApprovalArticleService{

}




