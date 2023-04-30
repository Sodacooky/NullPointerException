package soda.npe.common.utils;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.Map;

/**
 * 使用redis进行jwt的校验和密钥储存
 */
@Slf4j
@Component
public class JwtAuthUtil {

    @Resource
    @Qualifier("jwtRedisTemplate")
    private StringRedisTemplate jwtRedisTemplate;


    public String createToken(Map<String, Object> payload) {
        //generate key
        String key = UUID.randomUUID(true).toString(true);
        //generate token
        String token = JWTUtil.createToken(payload, key.getBytes());
        //store token with key to redis
        jwtRedisTemplate.opsForValue().set(token, key, Duration.ofDays(1));
        //return
        return token;
    }

    public boolean validation(String token) {
        //先判断有没有，没有就是过期了
        if (Boolean.FALSE.equals(jwtRedisTemplate.hasKey(token))) return false;
        //取出key
        String key = jwtRedisTemplate.opsForValue().get(token);
        Assert.notNull(key, "Redis中的token对应的key一定存在！");
        //校验
        return JWTUtil.verify(token, key.getBytes());
    }

    public boolean revoke(String token) {
        //先判断有没有，没有那么这个操作不存在，返回失败
        if (Boolean.FALSE.equals(jwtRedisTemplate.hasKey(token))) return false;
        //移除
        return Boolean.TRUE.equals(jwtRedisTemplate.delete(token));
    }

    public JSONObject getPayload(String token) {
        if (!this.validation(token)) return null; //不合法的token
        return JWT.of(token).getPayloads();
    }
}
