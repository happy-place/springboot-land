package com.bigdata.boot.chapter73.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName HomeController
 * @Description TODO
 * @Author HuHao（huhao1@cmcm.com）
 * @Date 2020/7/8 17:36
 * @Version 1.0
 **/
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Map<String, Object> model) {
        model.put("message", "Hello World");
        model.put("title", "Hello Home");
        model.put("date", new Date());
        return "home";
    }

    @RequestMapping("/foo")
    public String foo() {
        throw new RuntimeException("Expected exception in controller");
    }


}
