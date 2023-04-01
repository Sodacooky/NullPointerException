package soda.npe.servicearticle.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import soda.npe.common.constant.DBConstant;
import soda.npe.common.entity.Article;
import soda.npe.common.mapper.ArticleMapper;

import java.util.List;

@Service
public class ArticleService extends ServiceImpl<ArticleMapper, Article> {


    public List<Article> getPublishedBy(Long userId, Integer page) {
        //从数据库获取当前页的数据
        List<Article> found = this.list(new LambdaQueryWrapper<Article>()
                .eq(Article::getPublisherId, userId)
                .orderByDesc(Article::getPublisherId)
                .last("limit " + (DBConstant.PAGE_SIZE * (page - 1)) + "," + DBConstant.PAGE_SIZE));
        //移除text
        found.forEach(obj -> obj.setText(null));
        return found;
    }

}
