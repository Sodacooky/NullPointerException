package soda.npe.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import soda.npe.common.entity.QuestionInfo;

import java.util.List;

/**
 * @author sodac
 * @description 针对表【question_info(问题的信息)】的数据库操作Mapper
 * @createDate 2023-03-28 23:16:19
 * @Entity soda.npe.common.entity.QuestionInfo
 */
public interface QuestionInfoMapper extends BaseMapper<QuestionInfo> {

    /**
     * 按照时间顺序搜索问题，从问题的标题、标签、第一个Answer（正文）中匹配
     *
     * @param keyword  关键词
     * @param page     页码，from 1
     * @param pageSize 页大小
     * @param isAsc    asc
     * @return info实体类
     */
    List<QuestionInfo> searchInfoByTime(@Param("keyword") String keyword,
                                        @Param("page") Integer page,
                                        @Param("pageSize") Integer pageSize,
                                        @Param("isAsc") Boolean isAsc);

    /**
     * 按照订阅用户数量顺序搜索问题，从问题的标题、标签、第一个Answer（正文）中匹配
     *
     * @param keyword  关键词
     * @param page     页码，from 1
     * @param pageSize 页大小
     * @param isAsc    asc
     * @return info实体类
     */
    List<QuestionInfo> searchInfoBySubscriptionAmount(@Param("keyword") String keyword,
                                                      @Param("page") Integer page,
                                                      @Param("pageSize") Integer pageSize,
                                                      @Param("isAsc") Boolean isAsc);

}




