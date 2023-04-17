package soda.npe.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import soda.npe.common.entity.QuestionInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author sodac
 * @description 针对表【question_info(问题的信息)】的数据库操作Mapper
 * @createDate 2023-03-29 14:59:03
 * @Entity soda.npe.common.entity.QuestionInfo
 */
public interface QuestionInfoMapper extends BaseMapper<QuestionInfo> {

    List<QuestionInfo> searchInfoByTime(@Param("keyword") String keyword,
                                        @Param("page") Integer page,
                                        @Param("pageSize") Integer pageSize,
                                        @Param("isAsc") Boolean isAsc);

    List<QuestionInfo> searchInfoBySubscriptionAmount(@Param("keyword") String keyword,
                                                      @Param("page") Integer page,
                                                      @Param("pageSize") Integer pageSize,
                                                      @Param("isAsc") Boolean isAsc);

    List<QuestionInfo> searchInfoByAnswerAmount(@Param("keyword") String keyword,
                                                @Param("page") Integer page,
                                                @Param("pageSize") Integer pageSize,
                                                @Param("isAsc") Boolean isAsc);

    List<QuestionInfo> getWeeklyAnswerTop100(@Param("weekStart") Date weekStart);

    List<QuestionInfo> getMonthlyAnswerTop200(@Param("monthStart") Date monthStart);

    @MapKey("category")
    Map<String, Map<String, Object>> getHotCategories(@Param("monthStart") Date monthStart);
}




