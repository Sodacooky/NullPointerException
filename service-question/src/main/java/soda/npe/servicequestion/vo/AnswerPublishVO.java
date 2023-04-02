package soda.npe.servicequestion.vo;

import lombok.Data;

/**
 * 问题回答时，包含目标问题ID，回答正文的VO
 */
@Data
public class AnswerPublishVO {

    private Long questionId;

    private String text;

}
