package com.example.businessintelligencebackend.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@EnableOpenApi
@Configuration
public class SwaggerConfig {
    //测试
    /**
     * 配置Swagger2相关的bean
     */
    @Bean
    public Docket RestApi_Test() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.businessintelligencebackend.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 此处主要是API文档页面显示信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("商务智能期末项目") // 标题
               // .description("Economy Life实验后端") // 描述
                .version("1.0") // 版本
                .build();
    }

}



