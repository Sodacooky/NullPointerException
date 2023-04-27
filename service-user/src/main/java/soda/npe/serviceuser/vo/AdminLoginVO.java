package soda.npe.serviceuser.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 只有一个password字段，但为了能够正常反序列化而为之
 */
@Data
public class AdminLoginVO implements Serializable {
    private String password;
}
