package soda.npe.servicearticle.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import soda.npe.common.constant.DBConstant;
import soda.npe.common.entity.Article;
import soda.npe.common.entity.ArticleReply;
import soda.npe.common.entity.UserInfo;
import soda.npe.common.entity.UserNotice;
import soda.npe.common.mapper.ArticleMapper;
import soda.npe.common.mapper.ArticleReplyMapper;
import soda.npe.common.mapper.UserInfoMapper;
import soda.npe.common.mapper.UserNoticeMapper;
import soda.npe.servicearticle.vo.ArticleReplyVO;
import soda.npe.servicearticle.vo.ReplyPublishVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleReplyService extends ServiceImpl<ArticleReplyMapper, ArticleReply> {

    @Resource
    private UserNoticeMapper userNoticeMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private ArticleMapper articleMapper;

    public List<ArticleReplyVO> getOf(Long articleId, Integer page) {
        //取出回复列表
        List<ArticleReply> originReplys = this.list(new LambdaQueryWrapper<ArticleReply>()
                .eq(ArticleReply::getGoalArticleId, articleId)
                .orderByDesc(ArticleReply::getPublishTime)
                .last("limit " + (DBConstant.PAGE_SIZE * (page - 1)) + "," + DBConstant.PAGE_SIZE));
        //填充用户昵称与头像
        List<ArticleReplyVO> vos = new ArrayList<>(originReplys.size());
        originReplys.forEach(rep -> {
            UserInfo userInfo = userInfoMapper.selectById(rep.getPublisherId());
            ArticleReplyVO articleReplyVO = new ArticleReplyVO();
            articleReplyVO.setId(rep.getId());
            articleReplyVO.setPublisherId(rep.getPublisherId());
            articleReplyVO.setPublishTime(rep.getPublishTime());
            articleReplyVO.setGoalArticleId(rep.getGoalArticleId());
            articleReplyVO.setText(rep.getText());
            articleReplyVO.setPublisherAvatar(userInfo.getAvatar());
            articleReplyVO.setPublisherNickname(userInfo.getNickname());
            vos.add(articleReplyVO);
        });
        return vos;
    }


    public Long getReplyAmountOf(Long articleId) {
        return this.count(new LambdaQueryWrapper<ArticleReply>().eq(ArticleReply::getGoalArticleId, articleId));
    }

    public Boolean publish(Long userId, ReplyPublishVO replyPublishVO) {
        //构建reply对象
        ArticleReply articleReply = new ArticleReply();
        articleReply.setPublisherId(userId);
        articleReply.setText(replyPublishVO.getText());
        articleReply.setGoalArticleId(replyPublishVO.getArticleId());
        articleReply.setPublishTime(new Date());
        //储存
        if (!this.save(articleReply)) return false;
        //构建消息给文章作者
        // - 获取消息发送者信息和文章信息
        UserInfo replyOwner = userInfoMapper.selectById(articleReply.getPublisherId());
        Article article = articleMapper.selectById(articleReply.getGoalArticleId());
        // - 填充消息实体
        UserNotice userNotice = new UserNotice();
        userNotice.setTitle("文章 " + article.getTitle() + " 收到来自 " + replyOwner.getNickname() + " 的回复");
        userNotice.setGoalUserId(article.getPublisherId());
        userNotice.setTime(new Date());
        userNotice.setSupplement(articleReply.getGoalArticleId().toString());
        userNotice.setType("article_reply");
        userNotice.setIsRead(0);
        userNotice.setText(articleReply.getText());
        //储存
        return userNoticeMapper.insert(userNotice) == 1;
    }
}
