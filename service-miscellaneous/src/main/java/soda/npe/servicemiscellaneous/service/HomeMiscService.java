package soda.npe.servicemiscellaneous.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import soda.npe.common.entity.Advertisement;
import soda.npe.common.entity.Announcement;
import soda.npe.common.entity.Article;
import soda.npe.common.entity.QuestionInfo;
import soda.npe.common.mapper.*;
import soda.npe.servicemiscellaneous.vo.HotCategoriesVO;
import soda.npe.servicemiscellaneous.vo.SiteStateVO;

import java.time.Duration;
import java.util.*;

@Service
public class HomeMiscService {

    @Resource
    private QuestionInfoMapper questionInfoMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private AdvertisementMapper advertisementMapper;

    @Resource
    private AnnouncementMapper announcementMapper;

    @Resource
    private RedisTemplate<String, HotCategoriesVO> redisTemplate;

    public List<Advertisement> getAds() {
        return advertisementMapper.selectList(null);
    }

    public SiteStateVO getSiteState() {
        //生成今日开始时间
        DateTime beginOfDay = DateUtil.beginOfDay(new Date());
        //装填数据
        SiteStateVO siteStateVO = new SiteStateVO();
        siteStateVO.setTodayQuestionAmount(questionInfoMapper.selectCount(
                new LambdaQueryWrapper<QuestionInfo>().ge(QuestionInfo::getPublishTime, beginOfDay)));
        siteStateVO.setTodayArticleAmount(articleMapper.selectCount(
                new LambdaQueryWrapper<Article>().ge(Article::getPublishTime, beginOfDay)));
        siteStateVO.setTotalUserAmount(userInfoMapper.selectCount(null));
        //
        return siteStateVO;
    }

    public List<HotCategoriesVO> getHotCategories() {
        //判断redis中有没有，有的话就不再计算了，这是一个非常重量级的计算
        if (Boolean.TRUE.equals(redisTemplate.hasKey("homeHotCategories"))) {
            return redisTemplate.opsForList().range("homeHotCategories", 0, -1);
        }
        //30天开始时间
        DateTime beginTime = DateUtil.offsetDay(new Date(), -30);
        // category -> (column -> value)
        //统计月内问题中的分类数量
        Map<String, Map<String, Object>> hotCategoriesOfQuestion = questionInfoMapper.getHotCategories(beginTime);
        //统计月内文章中的分类数量
        Map<String, Map<String, Object>> hotCategoriesOfArticle = articleMapper.getHotCategories(beginTime);
        //合并重复
        // category -> amount
        TreeMap<String, Long> merged = new TreeMap<>();
        // fill question
        hotCategoriesOfQuestion.forEach((k, v) -> {
            long thisAmount = (long) v.get("amount");
            if (merged.containsKey(k)) {
                merged.put(k, merged.get(k) + thisAmount);
            } else {
                merged.put(k, thisAmount);
            }
        });
        // fill article
        hotCategoriesOfArticle.forEach((k, v) -> {
            long thisAmount = (long) v.get("amount");
            if (merged.containsKey(k)) {
                merged.put(k, merged.get(k) + thisAmount);
            } else {
                merged.put(k, thisAmount);
            }
        });
        //获取前八个
        List<HotCategoriesVO> result = new ArrayList<>();
        while (!merged.isEmpty() && result.size() < 8) {
            String biggestCategoryName = null;
            long biggestCategoryAmount = 0L;
            for (var pair : merged.entrySet()) {
                if (pair.getValue() > biggestCategoryAmount) {
                    biggestCategoryName = pair.getKey();
                    biggestCategoryAmount = pair.getValue();
                }
            }
            HotCategoriesVO hotCategoriesVO = new HotCategoriesVO();
            hotCategoriesVO.setId(result.size() + 1L);
            hotCategoriesVO.setCategory(biggestCategoryName);
            hotCategoriesVO.setAmount(biggestCategoryAmount);
            result.add(hotCategoriesVO);
            merged.remove(biggestCategoryName);
        }
        //
        redisTemplate.delete("homeHotCategories");
        result.forEach(v -> redisTemplate.opsForList().rightPush("homeHotCategories", v));
        redisTemplate.expire("homeHotCategories", Duration.ofHours(1));
        return result;
    }

    public List<Announcement> getAnnouncement() {
        //只获取前面四个
        return announcementMapper.selectList(new LambdaQueryWrapper<Announcement>().orderByDesc(Announcement::getTime).last("limit 4"));
    }
}
