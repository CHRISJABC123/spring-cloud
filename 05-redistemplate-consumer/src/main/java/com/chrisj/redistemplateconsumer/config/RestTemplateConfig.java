package com.chrisj.redistemplateconsumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Configuration：这个类是一个配置类
 */
@Configuration
public class RestTemplateConfig {

    @Bean//用来创建对象到spring容器当中
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
