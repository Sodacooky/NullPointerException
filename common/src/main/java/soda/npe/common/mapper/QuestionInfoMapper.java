package soda.npe.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import soda.npe.common.entity.QuestionInfo;

import java.util.List;

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

}




