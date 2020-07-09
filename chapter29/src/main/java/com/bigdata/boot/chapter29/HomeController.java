package com.bigdata.boot.chapter29;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName HomeController
 * @Description TODO
 * @Author HuHao（huhao1@cmcm.com）
 * @Date 2020/6/23 12:50
 * @Version 1.0
 **/

@Controller
@RequestMapping(value = "/")
public class HomeController {

    @GetMapping
    public String home(Model model) {
        model.addAttribute("message", "Hello World");
        model.addAttribute("title", "Hello Home");
        model.addAttribute("date", new Date());
        return "index";
    }

    @GetMapping(value = "/forward")
    public String forward(Map<String, Object> model) {
        model.put("message", "Hello World");
        model.put("title", "Hello Home");
        model.put("date", new Date());
        return "forward:/";
    }

    @GetMapping(value = "/redirect")
    public String redirect(Map<String, Object> model) {
        model.put("message", "Hello World");
        model.put("title", "Hello Home");
        model.put("date", new Date());
        return "redirect:http://www.baidu.com";
    }

    @RequestMapping("/exp")
    public String foo() {
        throw new RuntimeException("Expected exception in controller");
    }


}
