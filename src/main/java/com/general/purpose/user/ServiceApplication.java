package com.general.purpose.user;

import com.prperiscal.spring.resolver.projection.EnableProjectionResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//Custom Spring module, Check https://github.com/prperiscal/projectionResolver for more details.
@EnableProjectionResolver
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }
}
