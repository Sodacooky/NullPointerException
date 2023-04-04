package soda.npe.servicequestion.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 包含目标回复ID、回复正文，用户发布回复时使用的VO
 */
@Data
public class ReplyPublishVO implements Serializable {

    private Long answerId;

    private String text;

}
