package com.chrisj.resttemplateprovider.model;

/**
 * 姬动兴
 * 2021/1/26
 */
public class User {

    private String name;
    private Integer age;


    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public User setAge(Integer age) {
        this.age = age;
        return this;
    }
}
