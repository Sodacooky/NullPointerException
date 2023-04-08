package soda.npe.servicequestion.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class QuestionAnswerPreviewVO implements Serializable {

    private Long id;

    private Long questionId;

    private String questionTitle;

    private String shortText;

    private Long publisherId;

    private Date publishTime;

    private String publisherNickname;

    private String publisherAvatar;

    private Long approvalAmount;

}
