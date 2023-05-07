package soda.npe.servicemiscellaneous.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserReportPreviewVO implements Serializable {
    private Long id;
    private Long goalUserId;
    private Date time;
    private Integer isProcessed;

    private String goalUserNickname;
    private String goalUserAvatar;
    private String goalUserDescription;
}
