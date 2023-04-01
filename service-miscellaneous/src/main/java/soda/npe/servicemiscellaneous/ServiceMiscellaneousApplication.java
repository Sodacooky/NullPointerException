package soda.npe.servicemiscellaneous;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"soda.npe.servicemiscellaneous","soda.npe.common"})
@EnableDiscoveryClient
public class ServiceMiscellaneousApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceMiscellaneousApplication.class, args);
    }

}
