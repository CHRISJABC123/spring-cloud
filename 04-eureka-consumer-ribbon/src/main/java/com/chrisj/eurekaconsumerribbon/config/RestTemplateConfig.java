package com.chrisj.eurekaconsumerribbon.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

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


    /**
     * 配置消费者的负载均衡策略，
     *  new RandomRule() 随机
     *  WeightedResponseTimeRule  权重
     *  RetryRule  最大重试时间
     *
     */
    @Bean
    public IRule getIRule(){
        return new RandomRule();
    }
}
