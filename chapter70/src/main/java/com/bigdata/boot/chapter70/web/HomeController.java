package com.bigdata.boot.chapter70.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName HomeController
 * @Description TODO
 * @Author HuHao（huhao1@cmcm.com）
 * @Date 2020/7/8 06:58
 * @Version 1.0
 **/
@Controller
public class HomeController {

    @GetMapping("/")
    @Secured("ROLE_ADMIN")
    public String home(Map<String, Object> model) {
        model.put("message", "Hello World");
        model.put("title", "Hello Home");
        model.put("date", new Date());
        return "home";
    }
}


