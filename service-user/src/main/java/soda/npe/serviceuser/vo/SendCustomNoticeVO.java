package soda.npe.serviceuser.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SendCustomNoticeVO implements Serializable {
    private String goalUserId; //以空格间隔的目标用户id
    private String title;
    private String text;
}
