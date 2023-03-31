package soda.npe.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
@Slf4j
public class JwtAuthUtilRedisConfig {

    @Value("${spring.data.redis.host:localhost}")
    private String host;

    @Value("${spring.data.redis.port:6379}")
    private Integer port;

    @Bean("jwtRedisTemplate")
    public StringRedisTemplate jwtRedisTemplate(){
        //build connection factory with host,port,database
        LettuceConnectionFactory factory = new LettuceConnectionFactory(new RedisStandaloneConfiguration(this.host, this.port));
        factory.setDatabase(15);
        factory.afterPropertiesSet();
        //log
        log.info("Created 'JwtRedisTemplate' using host {}, port {}.",host,port);
        //build template
        return new StringRedisTemplate(factory);
    }

}
