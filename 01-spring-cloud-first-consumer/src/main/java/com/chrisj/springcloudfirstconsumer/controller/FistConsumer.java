package com.chrisj.springcloudfirstconsumer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * 服务消费者的控制器类，这里可以返回json或者视图页面  ，但是我们使用@RestController仅仅是为了方便测试
 */
@RestController
public class FistConsumer {

    /**
     * 用这个类中提供的方法来通过http远程访问服务提供者
     * 由于spring容器中默认没有这个对象，所以我们必须通过配置类手动创建对象
     * 具体参考RestTemplateConfig类
     */
    @Resource
    private RestTemplate restTemplate;
    @RequestMapping("/test")
    public Object test(){
        /**
         * getForEntity方法，使用http协议以get方式提交请求访问服务的提供者，对应服务提供者的@RequestMapping和@GetMapping
         * 参数1 为需要访问的具体请求路径
         * 参数2 远程服务返回的数据，服务提供者返回的全部是json类型的字符串数据
         *       如果远程服务返回的是基本的数据类型，我们可以使用String.class或者Integer.class来接收
         *       如果远程服务返回的是json格式的字符串类型的数据，我们可以使用String.class来接受，用FastJson等工具解析
         *       转换成java对象或者直接使用Map.class或者实体类.class来封装数据spring会直接将这个json字符串封装成对应的数据类型
         *       如果远程服务返回的是接送格式的字符串数组类型的数据，我们可以使用String.class来接受，用FastJson等工具来解析
         *       转换成java对象，或者直接使用List.class或者Set.class来封装数据，spring会直接将这个json字符串封装成对应的数
         *       类型
         * 返回值类型为ResponseEntity<T>,这个对象为spring的响应封装对象，可以通过这个对象获取响应的头文件信息，响应状态码，
         *     以及响应体（具体的响应数据），泛型为getForEntity方法的参数2
         */
        String url="http://localhost:8081/test";
        ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getStatusCodeValue());
        System.out.println(entity.getHeaders());
        Object obj=entity.getBody();
        return "第一个spring-cloud 消费者--------"+obj;
    }
}
