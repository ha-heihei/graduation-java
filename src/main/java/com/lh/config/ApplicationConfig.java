package com.lh.config;

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

/**
 * @author LiHao
 * @create 20220113 20:23
 */
@Configuration
public class ApplicationConfig {


    /**
     * restTemplate配置
     * @return
     */
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    /**
     * 分页配置
     * @return
     */
    @Bean
    public PaginationInnerInterceptor getPaginationInnerInterceptor(){
        return new PaginationInnerInterceptor();
    }

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
