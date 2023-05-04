package soda.npe.serviceuser.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdatePasswordVO implements Serializable {
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
}
