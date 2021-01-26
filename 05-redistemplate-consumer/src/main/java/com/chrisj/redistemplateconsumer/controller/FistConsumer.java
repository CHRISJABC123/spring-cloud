package com.chrisj.redistemplateconsumer.controller;

import com.chrisj.redistemplateconsumer.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务消费者的控制器类，这里可以返回json或者视图页面  ，但是我们使用@RestController仅仅是为了方便测试
 */
@RestController
public class FistConsumer {

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("/getUser")
    public Object getUser(){
        /**
         * 由于服务提供者返回的数据是一个服务Json对象格式的字符串数据，因此我们可以使用String.class来接收数据
         * 然后在使用一些Json工具将这个字符串转换成对应的java对象
         * 或直接使用一个实体类或Map集合来封装数据Spring会自动将这个Json字符串转换成对应的java对象
         */
        String url="http://localhost:8081/getUser";
        ResponseEntity<User> entity = restTemplate.getForEntity(url, User.class);
        User user=entity.getBody();
        return "第一个spring-cloud 消费者--------"+user;
    }

    @RequestMapping("/getUserList")
    public Object getUserList(){
        /**
         * 由于服务提供者返回的数据是一个服务Json对象数组格式的字符串数据，因此，我们可以使用String.class来接收数据
         * 然后在使用一些Json工具将这个字符串转换成对应的java对象
         * 或直接使用一个List集合或Set集合来封装数据Spring会自动将这个Json字符串转换成对应的java对象
         * 注意：
         *   如果使用List或Set集合封装json对象数组，那么集合中的元素类型默认是LinkedHashMap,也只能是LinkedHashMap
         */
        String url="http://localhost:8081/getUserList";
        ResponseEntity<List> entity = restTemplate.getForEntity(url, List.class);
        List list =entity.getBody();
        for (Object o : list) {
            System.out.println(o.getClass());
        }
        return "第一个spring-cloud 消费者--------"+list;
    }

    @RequestMapping("/params")
    public Object params01(){
        /**
         * 我们可以使用字符串拼接的方式拼接一个请求地址路径将动态数据拼接进去
         * 例如"http://localhost:8081/params?name="+name+"&age="+age;
         * 我们也可以使用占位符来动态进行赋值，其中{0}和{1}都是请求的占位符需要后期动态赋值
         * 需要使用getForEntity方法的参数3来为地址路径中的占位符进行赋值，参数3取值为Object的可变长参数
         * 可以传递一个Object类型的数组，占位符中的0或1需要与参数3这个数组的索引对应就可以动态为占位符进行赋值
         * 注意：
         *   1、如果使用数组传递请求参数那么要求我们必须清楚知道数组中的每个元素的具体类型以及含义并正确的与占位符所对应
         *   2、数组不能直接接收用户从页面中传递过来的数据
         *   3、除非请求参数非常的少例如只有1个我们建议使用这个方法传递参数否则不推荐
         */
        String url="http://localhost:8081/params?name={0}&age={1}";
        Object params[]= {"王五",22};
        ResponseEntity<User> entity = restTemplate.getForEntity(url, User.class, params);
        User user = entity.getBody();
        return "服务消费者-------------"+ user;
    }
    @RequestMapping("/mapParams")
    public Object params02(Map userParams){
        /**
         * getForEntity 方法的参数3 为请求的动态参数数 取值为Map集合，用于为请求地址路径中的占位符动态赋值
         * 其中地址路径中的{name}和{age}为占位符需要动态赋值，占位符的名字name和age需要与getForEntity方法的
         * 参数3中的Map集合的key所对应就可以动态赋值
         *  注意：
         *    1、无论使用数组还是Map集合传递参数，占位符可以出现在地址路径中的任何一个位置中例如
         *       http://localhost:8081/{4}?{2}={0}&{3}={1}，但是不建议这么做
         *    2、如果需要传递的数据数量比较多建议使用Map作为参数
         */
        String url="http://localhost:8081/params?name={name}&age={age}";
        Map<String,Object> params=new HashMap();
        params.put("name", "古天乐");
        params.put("age", 32);
        ResponseEntity<User> entity = restTemplate.getForEntity(url, User.class, params);
        User user = entity.getBody();
        return "服务消费者-------------"+ user;
    }
    @RequestMapping("/getForObject")
    public Object getForObject(){
        String url="http://localhost:8081/params?name={name}&age={age}";
        Map<String,Object> params=new HashMap();
        params.put("name", "陈小春");
        params.put("age", 34);
        User user = restTemplate.getForObject(url, User.class, params);
        return "服务消费者-------------"+ user;
    }

    @RequestMapping("/postTest")
    public Object postTest(){
        /**
         * post相关方法，使用上与get相关方法基本类似，它是使用Http协议以Post方式提交请求 对应服务提供者的@PostMapping或@RequestMapping
         * 参数 1 为请求地址路径
         * 参数 2 本次请求的请求体（Post请求参数）
         * 参数 3 为本次响应的数据封装类型
         * 参数 4 为请求地址路径的动态参数
         * 注意：
         *   1、参数2 为请求体取值为Object类型但是却不能任意传递数据，应该传递一个Map集合这个Map集合的value
         *      必须要泛型为List或Map集合的一个子类LinkedMultiValueMap
         *   2、post请求速度慢于get请求，post请求参数量理论没有上限，而get请求不同的浏览器参数数量量不同最少的约2K
         *     因此大多数请求我们都会使用get相关方法除非服务提供者明确使用了PostMapping
         */
        String url="http://localhost:8081/postTest";
        LinkedMultiValueMap params=new LinkedMultiValueMap<>();
        /**
         * add 方法是来自LinkedMultiValueMap的，用于添加数据
         * 如果key为name在这个Map集合中存在则把张家辉存入key为name，value为List的集合尾部
         * 如果key为name在这个Map集合中不存在则创建一个List集合，然后将张家辉存入这个List再将List集合添加到
         * key为name 的 value中
         */
        params.add("name", "张家辉");
        params.add("age", 28);
        User user = restTemplate.postForObject(url,params,User.class);
        return "服务消费者-------------"+ user;
    }

    @RequestMapping("/putTest")
    public Object putTest(){
        String url="http://localhost:8081/putTest";
        LinkedMultiValueMap params=new LinkedMultiValueMap<>();
        /**
         * put方法 使用Http协议已Put方式提交请求，对应的服务提供者的@PutMapping
         * 参数 1 为请求地址路径
         * 参数 2 为请求体（请求参数）
         * 参数 3 为url动态数据
         * 注意：
         *   1、put方法对应数据的修改，这个方法没有返回值因此我们不知道本次请求是否成功，因此
         *   不推荐使用除非服务提供者明确使用了@PutMapping
         */
        params.add("name", "张家辉");
        params.add("age", 28);
        restTemplate.put(url, params);
        return "服务消费者-------------";
    }

    @RequestMapping("/deleteTest")
    public Object deleteTest(){
        /**
         * delete相关方法。使用Http协议以Delete方式提交请求对应服务提供者的@DeleteMapping
         * 参数 1 为请求地址路径
         * 参数 2 为url的动态参数
         * 注意：
         *   1、delete方法主要针对数据的删除，没有没有返回值因此我们不知道操作是否成功所以不推荐使用
         *   除非服务提供者明确使用了@DeleteMapping
         */
        String url="http://localhost:8081/deleteTest?id={0}";
        restTemplate.delete(url,1);
        return "服务消费者-------------";
    }

}
