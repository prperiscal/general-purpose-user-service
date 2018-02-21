package com.mytasks.user;

import com.prperiscal.resolver.projection.EnableProjectionResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProjectionResolver
//Custom Spring module, Check https://github.com/prperiscal/projectionResolver for more details.
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }
}