package soda.npe.serviceuser.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录时要传输的内容
 */
@Data
public class LoginVO implements Serializable {

    private String email;

    private String password;

}
