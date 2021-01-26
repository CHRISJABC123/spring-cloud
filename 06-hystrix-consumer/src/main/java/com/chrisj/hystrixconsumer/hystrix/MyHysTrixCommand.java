package com.chrisj.hystrixconsumer.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.web.client.RestTemplate;

/**
 * 姬动兴
 * 2021/1/26
 */
/**
 * 自定义异常熔断器类，用于针对远程服务进行熔断处理的
 * 需要继承熔断器的抽象父类,父类泛型决定远程服务的返回数据，以及熔断后的响应数据类型
 */
public class MyHysTrixCommand extends HystrixCommand<String> {

    //准备restTemplate和 url 在这个自定义熔断器类里发送请求
    private RestTemplate restTemplate;

    private String url;

    public MyHysTrixCommand(RestTemplate restTemplate,String url) {
        //由于父类没有无参构造因此必须使用super调用有参构造方法
        //将protect改为public
        //这个参数Setter用于设置熔断器属性
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("")));
        this.restTemplate=restTemplate;
        this.url=url;
    }

    /**
     * run 方法用于具体的调用某个远程服务并返回远程服务的响应数据给消费者
     * 这个方法不能手动调用，返回值类型取决父抽象类HystrixCommand的泛型
     * @return
     * @throws Exception
     */
    @Override
    protected String run() throws Exception {
        return restTemplate.getForObject(url, String.class);
    }

    /**
     * 服务降级方法，当run方法触发服务的熔断之后，会自动调用这个方法来返回服务降级的响应数据
     */
    protected String getFallback() {
        return "远程服务熔断了";
    }
}
