package soda.npe.common.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * 统一请求响应
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response implements Serializable {
    //响应状态码，在原有HTTP200成功的基础上，揭示服务的运行结果并辅助DEBUG
    //0为正常，其他值为失败
    private Integer code;

    //响应消息，一般用作失败时DEBUG
    private String message;

    //携带的数据
    private Object data;

    public static Response ok(String message, Object data) {
        return new Response(0, message, data);
    }

    public static Response fail(Integer code, String message) {
        if (code != null) Assert.isTrue(code != 0, "当为失败时code不应该为0");
        else code = -1;
        return new Response(code, message, null);
    }

}
