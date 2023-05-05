package soda.npe.servicearticle.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DoArticleReplyVO implements Serializable {

    private Long id;

    private Long goalArticleId;

    private String text;

    private Long publisherId;

    private Date publishTime;

    private String publisherNickname;

    private String publisherAvatar;
}
