package soda.npe.servicemiscellaneous.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ReplyReportPreviewVO implements Serializable {
    private Long id;
    private Long goalArticleReplyId;
    private Date time;
    private Integer isProcessed;

    private String goalArticleTitle;
    private String goalArticleReplyShortText;
    private String goalOwnerNickname;
    private String goalOwnerAvatar;
}
