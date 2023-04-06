package soda.npe.servicequestion.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class QuestionAnswerReadingVO implements Serializable {

    private Long id;

    private Long questionId;

    private String text;

    private Integer orderNumber;

    private Long publisherId;

    private Date publishTime;

    private String publisherNickname;

    private String publisherAvatar;

    private Long approvalAmount;

}
