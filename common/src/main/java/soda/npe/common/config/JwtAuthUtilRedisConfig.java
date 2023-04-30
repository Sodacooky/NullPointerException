package soda.npe.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

//JwtAuthUtil内部使用的StringRedisTemplate，只是切换到了数据库15而已
@Slf4j
@Configuration
public class JwtAuthUtilRedisConfig {
    //redis host，默认localhost
    @Value("${spring.data.redis.host:localhost}")
    private String host;

    //redis port，默认6479
    @Value("${spring.data.redis.port:6379}")
    private Integer port;

    @Bean("jwtRedisTemplate")
    public StringRedisTemplate JwtAuthUtil() {
        //build connection factory with host,port,database
        LettuceConnectionFactory factory = new LettuceConnectionFactory(new RedisStandaloneConfiguration(this.host, this.port));
        factory.setDatabase(15);
        factory.afterPropertiesSet();
        //log
        log.info("Created 'JwtRedisTemplate' using host {}, port {}.", host, port);
        //build template
        return new StringRedisTemplate(factory);
    }

}
