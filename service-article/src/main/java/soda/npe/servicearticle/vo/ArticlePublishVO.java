package soda.npe.servicearticle.vo;

import lombok.Data;

/**
 * 文章发布时使用的VO，包括标题、分类、正文
 */
@Data
public class ArticlePublishVO {

    private String title;

    private String category;

    private String text;

}
