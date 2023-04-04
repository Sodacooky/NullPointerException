package soda.npe.servicearticle;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"soda.npe.servicearticle", "soda.npe.common"})
@EnableDiscoveryClient
@MapperScan("soda.npe.common.mapper")
public class ServiceArticleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceArticleApplication.class, args);
    }

}
