package com.eurekaclusterconsumer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * 服务消费者的控制器类，这里可以返回json或者视图页面  ，但是我们使用@RestController仅仅是为了方便测试
 */
@RestController
public class EurekaClientConsumer {

    @Resource
    private RestTemplate restTemplate;
    @RequestMapping("/test")
    public Object test(){
        /**
         * 指定请求访问地址路径，其中02-EUREKA-CLIENT-PROVIDER 为服务名，这个服务名不区分大小写
         * 用于到注册中心中获取服务的清单
         * 注意：
         *   由于在注册中心中每个服务名所对应的清单列表可能会有多个访问地址，因此我们必须要让当前的RestTemplate类
         *   能够支持负载均衡才可，否则无法访问远程服务
         *   具体负载均衡配置参考RestTemplateConfig类
         */
        String url="http://03-eureka-cluster-provider/test";
        ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);
        Object obj=entity.getBody();
        return "第一个eurekaClient消费者--------"+obj;
    }
}
