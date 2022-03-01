package com.lh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @author LiHao
 * @create 2022-02-12 17:52
 */
@Component
@EnableSwagger2  // 开启swagger自动配置
public class SwaggerConfigure {

    // controller路径
    private final String basePackage = "com.lh.controller";

    /**
     * 配置接口扫描
     *     1.RequestHandlerSelectors.basePackage(basePackage)  扫描具体路径
     *     2.RequestHandlerSelectors.any() 扫描所有接口
     *     3.RequestHandlerSelectors.none() 不扫描接口
     *     4.RequestHandlerSelectors.withMethodAnnotation()  通过方法上的注解扫描
     *     5.RequestHandlerSelectors.withClassAnnotation() 通过类上的注解扫描
     * @return Docket
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                // 配置path过滤规则
                // PathSelectors.any()
                // PathSelectors.none()
                // PathSelectors.regex()
                //.paths(PathSelectors.ant("/*"))
                .build();
    }

    private ApiInfo apiInfo() {
        // 接口联系人信息
        Contact contact = new Contact("HXY", "", "857269545@qq.com");
        // 接口信息
        return new ApiInfo("honeybee", "honeybee", "v1.0",
                "http://localhost:8080", contact, "Honeybee 1.0",
                "", new ArrayList<>());
    }
}

