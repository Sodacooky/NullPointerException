package soda.npe.servicearticle.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 文章回复时使用的VO，包括目标文章和正文
 */
@Data
public class DoReplyPublishVO implements Serializable {

    private Long articleId;

    private String text;

}
