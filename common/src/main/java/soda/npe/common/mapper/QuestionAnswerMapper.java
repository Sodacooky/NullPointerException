package soda.npe.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import soda.npe.common.entity.QuestionAnswer;

import java.util.List;

/**
 * @author sodac
 * @description 针对表【question_answer(对问题的回答，包括一楼的问题详细内容)】的数据库操作Mapper
 * @createDate 2023-03-29 14:59:03
 * @Entity soda.npe.common.entity.QuestionAnswer
 */
public interface QuestionAnswerMapper extends BaseMapper<QuestionAnswer> {

    List<QuestionAnswer> getOfQuestionByApproval(@Param("questionId") Long questionId,
                                                @Param("page") Integer page,
                                                @Param("pageSize") Integer pageSize,
                                                @Param("isAsc") Boolean isAsc);

    List<QuestionAnswer> searchAnswerByApproval(@Param("keyword") String keyword,
                                                @Param("page")Integer page,
                                                @Param("isAsc") Boolean isAsc);

}




