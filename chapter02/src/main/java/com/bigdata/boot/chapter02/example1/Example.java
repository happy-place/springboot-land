package com.bigdata.boot.chapter02.example1;
/**
 * @ClassName Example
 * @Description TODO
 * @Author HuHao（huhao1@cmcm.com）
 * @Date 2020/6/8 20:03
 * @Version 1.0
 **/

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration // 自动配置项目
@RequestMapping(value = "/simple-rest") // 当前controller映射的路径
public class Example {

    @GetMapping(value = "/hi") // 当前方法映射的路径
    public String sayHi(){
        return "hello world!";
    }

    public static void main(String[] args) {
        // Controller与Application写在一起
        SpringApplication.run(Example.class,args);
    }

}
