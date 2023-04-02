package soda.npe.servicearticle.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import soda.npe.common.constant.DBConstant;
import soda.npe.common.entity.ArticleReply;
import soda.npe.common.mapper.ArticleReplyMapper;
import soda.npe.servicearticle.vo.ReplyPublishVO;

import java.util.Date;
import java.util.List;

@Service
public class ArticleReplyService extends ServiceImpl<ArticleReplyMapper, ArticleReply> {

    public List<ArticleReply> getOf(Long articleId, Integer page) {
        return this.list(new LambdaQueryWrapper<ArticleReply>()
                .eq(ArticleReply::getGoalArticleId, articleId)
                .orderByDesc(ArticleReply::getPublishTime)
                .last("limit " + (DBConstant.PAGE_SIZE * (page - 1)) + "," + DBConstant.PAGE_SIZE));
    }

    public Long getReplyAmountOf(Long articleId) {
        return this.count(new LambdaQueryWrapper<ArticleReply>().eq(ArticleReply::getGoalArticleId, articleId));
    }

    public Boolean publish(Long userId, ReplyPublishVO replyPublishVO) {
        ArticleReply articleReply = new ArticleReply();
        articleReply.setPublisherId(userId);
        articleReply.setText(replyPublishVO.getText());
        articleReply.setGoalArticleId(replyPublishVO.getArticleId());
        articleReply.setPublishTime(new Date());
        return this.save(articleReply);
    }
}
