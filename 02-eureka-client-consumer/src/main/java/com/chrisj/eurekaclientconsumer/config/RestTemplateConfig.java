package com.chrisj.eurekaclientconsumer.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Configuration：这个类是一个配置类
 */
@Configuration
public class RestTemplateConfig {

    /**
     * @LoadBalanced 标记当前RestTemplate支持Ribbon的负载均衡，默认负载均衡配置为轮询
     */
    @LoadBalanced
    @Bean//用来创建对象到spring容器当中
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
