package soda.npe.servicemiscellaneous.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ArticleReportPreviewVO implements Serializable {
    private Long id;
    private Long goalArticleId;
    private Date time;
    private Integer isProcessed;

    private String goalArticleTitle;
    private String goalArticleShortText;
    private String goalOwnerNickname;
    private String goalOwnerAvatar;
}
