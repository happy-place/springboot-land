package com.bigdata.boot.chapter01.example2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName SimpleController
 * @Description TODO
 * @Author HuHao（huhao1@cmcm.com）
 * @Date 2020/6/9 11:23
 * @Version 1.0
 **/

@RestController
@RequestMapping(value = "/simple")
public class SimpleController {

    @GetMapping(value = "/hi")
    public String sayHi(){
        return "Hello, world.";
    }

}
