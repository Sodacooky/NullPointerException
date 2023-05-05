package soda.npe.serviceuser.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModifyUserVO implements Serializable {
    private Long id;
    private String nickname;
    private String description;
    private String avatar;
}
