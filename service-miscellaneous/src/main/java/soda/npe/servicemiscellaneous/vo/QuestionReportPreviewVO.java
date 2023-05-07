package soda.npe.servicemiscellaneous.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class QuestionReportPreviewVO implements Serializable {
    private Long id;
    private Long goalQuestionId;
    private Date time;
    private Integer isProcessed;

    private String goalQuestionTitle;
    private String goalQuestionShortText;
    private String goalOwnerNickname;
    private String goalOwnerAvatar;
}
