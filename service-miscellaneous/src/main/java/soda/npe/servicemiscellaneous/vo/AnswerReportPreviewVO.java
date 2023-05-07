package soda.npe.servicemiscellaneous.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AnswerReportPreviewVO implements Serializable {
    private Long id;
    private Long goalQuestionAnswerId;
    private Date time;
    private Integer isProcessed;

    private String goalQuestionTitle;
    private String goalQuestionAnswerShortText;
    private String goalOwnerNickname;
    private String goalOwnerAvatar;
}
