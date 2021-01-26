package com.chrisj.eurekaproviderribbon.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 姬动兴
 * 2021/1/24
 */
@RestController
public class EurekaProvider {

    @RequestMapping("/test")
    public Object firstProvider(){
        return "提供者8081";
    }
}
