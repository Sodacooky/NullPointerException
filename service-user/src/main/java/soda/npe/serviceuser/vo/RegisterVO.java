package soda.npe.serviceuser.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 注册数据
 */
@Data
public class RegisterVO implements Serializable {

    private String email;

    private String nickname;

    private String password;

    private String confirmPassword;

    private String code;

}
