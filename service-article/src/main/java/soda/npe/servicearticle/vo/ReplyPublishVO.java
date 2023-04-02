package soda.npe.servicearticle.vo;

import lombok.Data;

/**
 * 文章回复时使用的VO，包括目标文章和正文
 */
@Data
public class ReplyPublishVO {

    private Long articleId;

    private String text;

}
