package soda.npe.servicequestion.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 包含问题信息和问题正文，用户发布文章时使用的VO
 */
@Data
public class QuestionPublishVO implements Serializable {


    /**
     * 问题的标题、概述
     */
    private String title;


    /**
     * 问题的分类
     */
    private String category;

    /**
     * 问题的正文
     */
    private String text;


}
