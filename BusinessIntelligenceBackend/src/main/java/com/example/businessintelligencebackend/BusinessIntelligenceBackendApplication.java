package com.example.businessintelligencebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@SpringBootApplication
public class BusinessIntelligenceBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessIntelligenceBackendApplication.class, args);
    }

}
