package com.chrisj.eurekaclientprovider.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 姬动兴
 * 2021/1/24
 */
@RestController//因为springcloud是基于http协议restful风格的所以建议用这个注解创建Cotroller对象
public class EurekaProvider {

    @RequestMapping("/test")//类上面用了@RestController整个类中所有的方法默认添加了@responseBody
    public Object firstProvider(){
        return "第一个eurekaClient 提供者";
    }
}
