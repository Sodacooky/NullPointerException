package soda.npe.serviceuser.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateInfoVO implements Serializable {
    private String nickname;
    private String description;
}
