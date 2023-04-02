package soda.npe.servicequestion.vo;

import lombok.Data;

/**
 * 包含目标回复ID、回复正文，用户发布回复时使用的VO
 */
@Data
public class ReplyPublishVO {

    private Long answerId;

    private String text;

}
