package com.general.purpose.user;

import com.prperiscal.spring.resolver.projection.EnableProjectionResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
//Custom Spring module, Check https://github.com/prperiscal/projectionResolver for more details.
@EnableDiscoveryClient
@EnableProjectionResolver
@EnableResourceServer
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }
}
