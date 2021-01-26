package com.chrisj.hystrixconsumer.controller;
import com.chrisj.hystrixconsumer.hystrix.MyHysTrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;


@RestController
public class ConsumerController {

    @Resource
    private RestTemplate restTemplate;
    /**
     *  @HystrixCommand 标记当前方法需要使用Hystrix的服务熔断
     *  如果当前方法抛出异常或执行超时都会触发服务的熔断
     *  属性：
     *    fallbackMethod 用于指定当前控制器类中的某个方法的方法名，如果标记了HystrixCommand的方法触发服务熔断后
     *    将会自动调用fallbackMethod所指定的这个方法来替代服务的真实返回信息给用户
     */
    @HystrixCommand(fallbackMethod = "error")
    @RequestMapping("/test01")
    public Object test01(){
        String url="http://06-hystrix-provider:8081/test01";
        String result=restTemplate.getForObject(url, String.class);
        return "消费者======"+result;
    }
    /**
     *  @HystrixCommand 标记当前方法需要使用Hystrix的服务熔断
     *  如果当前方法抛出异常或执行超时都会触发服务的熔断
     *  属性：
     *    commandProperties 用于指定熔断器的属性取值为 @HystrixProperty的数组，同时可以指定多个属性
     *       @HystrixProperty 主要作用是指定熔断器的某个具体属性他是一个K/V结构
     *         属性
     *          name  属性名 ，execution.isolation.thread.timeoutInMilliseconds表示服务的执行的超时时间
     *          value 属性值 3000 表示超时时间为3000毫秒
     *   注意：
     *     我们指定了服务的执行超时时间为3000毫秒，那么就是允许当前服务在3000毫秒内执行完成，如果当前服务没有在
     *     3000毫秒内执行完成就会触发服务的熔断，默认的超时时间为1000毫秒
     */
    @HystrixCommand(fallbackMethod = "error",commandProperties = @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="3000"))
    @RequestMapping("/test02")
    public Object test02(){
        String url="http://06-hystrix-provider:8081/test02";
        String result=restTemplate.getForObject(url, String.class);
        return "消费者======"+result;
    }

    @HystrixCommand(fallbackMethod = "error")
    @RequestMapping("/test03")
    public String test03(){
        String  str=null;
        System.out.println(str.toString());
        String url="http://06-hystrix-provider/test02";
            ResponseEntity<String> result= restTemplate.getForEntity(url,String.class);
        String body=result.getBody();
        return "服务消费者-----"+body;
    }

    @HystrixCommand(fallbackMethod = "error",ignoreExceptions = {NullPointerException.class})
    @RequestMapping("/test04")
    public String test04(){
        String  str=null;
        System.out.println(str.toString());
        String url="http://06-hystrix-provider/test02";
        ResponseEntity<String> result= restTemplate.getForEntity(url,String.class);
        String body=result.getBody();
        return "服务消费者-----"+body;
    }

    @HystrixCommand(fallbackMethod = "error")
    @RequestMapping("/test05")
    public String test05(){

        String url="http://06-hystrix-provider/test01";
        MyHysTrixCommand command=new MyHysTrixCommand(restTemplate,url);
        //执行自定义熔断类，会自定调用run方法
        String execute = command.execute();
        return "服务消费者-----"+execute;
    }
    /**
     * 服务的降级方法，当某个标记了@HystrixCommand注解的方法触发了服务熔断那么就会自动调用这个方法
     * 来替代服务真实的返回内容，虽然这个服务降级方法返回的数据不是用户最终期望的那个但是好过出现异常或
     * 执行超时
     * 形参 Throwable throwable 为触发服务熔断时的异常对象，所有的服务熔断触发都会抛出一个异常
     * 我们需要根据这个异常对象，来返回不同的异常提示信息
     * 注意：
     *  1、如果是远程服务不管出现什么异常那么对于消费者来说都是服务器内部错误抛出HttpServerErrorException$InternalServerError
     *  2、如果是执行超时则会抛出一个HystrixTimeoutException
     *  3、如果是当前服务内部出现了异常则抛出对应的异常信息例如NullPointerException
     *  4、我们要根据不同的异常类型记录不同的日志并被用户返回不同的响应数据
     *
     */
    public String  error(Throwable throwable){
        System.out.println(throwable.getClass());
        return "触发了服务熔断";
    }
}
