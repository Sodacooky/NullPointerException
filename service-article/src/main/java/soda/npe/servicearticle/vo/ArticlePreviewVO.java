package soda.npe.servicearticle.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ArticlePreviewVO {

    private Long id;

    private String title;

    private String shortText;

    private String category;

    private Long publisherId;

    private Date publishTime;

    private String publisherNickname;

    private String publisherAvatar;

    private Long approvalAmount;

    private Long replyAmount;
}
