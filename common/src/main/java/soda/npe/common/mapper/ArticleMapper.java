package soda.npe.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import soda.npe.common.entity.Article;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author sodac
 * @description 针对表【article(文章)】的数据库操作Mapper
 * @createDate 2023-03-29 14:59:03
 * @Entity soda.npe.common.entity.Article
 */
public interface ArticleMapper extends BaseMapper<Article> {

    List<Article> searchByApproval(@Param("keyword") String keyword,
                                   @Param("page") Integer page,
                                   @Param("pageSize") Integer pageSize,
                                   @Param("isAsc") Boolean isAsc);

    List<Article> searchByReplyAmount(@Param("keyword") String keyword,
                                      @Param("page") Integer page,
                                      @Param("pageSize") Integer pageSize,
                                      @Param("isAsc") Boolean isAsc);

    List<Article> getWeeklyReplyTop100(@Param("weekStart") Date weekStart);

    List<Article> getMonthlyReplyTop200(@Param("monthStart") Date monthStart);

    @MapKey("category")
    Map<String, Map<String, Object>> getHotCategories(@Param("monthStart") Date monthStart);
}




