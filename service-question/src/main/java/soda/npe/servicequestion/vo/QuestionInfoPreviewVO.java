package soda.npe.servicequestion.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 在原有的QuestionInfo的基础上添加了一段摘要、回答数量、订阅数量等
 */
@Data
public class QuestionInfoPreviewVO implements Serializable {

    private Long id;

    private String title;

    private Long publisherId;

    private Date publishTime;

    private String category;

    private String shortText;

    private String publisherNickname;

    private String publisherAvatar;

    private Long subscriptionAmount;

    private Long answerAmount;

}
