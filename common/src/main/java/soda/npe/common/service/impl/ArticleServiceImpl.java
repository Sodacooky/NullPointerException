package soda.npe.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import soda.npe.common.entity.Article;
import soda.npe.common.service.ArticleService;
import soda.npe.common.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

/**
* @author sodac
* @description 针对表【article(文章)】的数据库操作Service实现
* @createDate 2023-03-28 23:16:19
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{

}




