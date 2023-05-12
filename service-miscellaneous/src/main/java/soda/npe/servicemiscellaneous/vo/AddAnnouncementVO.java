package soda.npe.servicemiscellaneous.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddAnnouncementVO implements Serializable {
    private String title;
    private String text;
}
