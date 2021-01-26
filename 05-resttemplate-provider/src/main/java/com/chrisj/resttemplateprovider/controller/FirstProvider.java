package com.chrisj.resttemplateprovider.controller;

import com.chrisj.resttemplateprovider.model.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 姬动兴
 * 2021/1/24
 */
@RestController
public class FirstProvider {

    @RequestMapping("/getUser")
    public Object getUser(){
        //返回一个User对象，底层Spring会将这个对象转换为对应的Json格式的字符串数据
        return new User("张家辉", 30);
    }

    @RequestMapping("/getUserList")
    public Object getUserList(){
        List list=new ArrayList();
        list.add(new User("张家辉", 30));
        list.add(new User("古天乐", 33));
        list.add(new User("陈小春", 40));
        //返回一个list，底层Spring会将这个对象转换为对应的Json格式的字符串数据
        return list;
    }

    @RequestMapping("/params")
    public Object params(String name,Integer age){
        return new User(name, age);
    }

    @RequestMapping("/postTest")
    public Object postTest(String name,Integer age){
        return new User(name, age);
    }

    @PutMapping("/putTest")
    public Object putTest(String name,Integer age){
        System.out.println(name+"============"+age);
        return new User(name, age);
    }

    @DeleteMapping("/deleteTest")
    public Object putTest(Integer id){
        System.out.println(id);
        return "删除成功";
    }
}
