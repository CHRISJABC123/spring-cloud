package com.chrisj.hystrixprovider.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProviderController {

    @RequestMapping("/test01")
    public Object test01(){
        System.out.println(10/0);
        return "服务提供者test01";
    }
    @RequestMapping("/test02")
    public Object test02(){
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("---------------------");
        return "服务提供者test02";
    }
}
