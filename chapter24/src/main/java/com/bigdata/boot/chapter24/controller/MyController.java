package com.bigdata.boot.chapter24.controller;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import com.bigdata.boot.chapter24.model.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyController {

	@PostConstruct
	public void slowRestart() throws InterruptedException {
		// 控制器创建后休眠5s，再启动
		Thread.sleep(5000);
	}

	@GetMapping("/")
	public ModelAndView get(HttpSession session) {
		Object sessionVar = session.getAttribute("var");
		if (sessionVar == null) {
			sessionVar = new Date();
			session.setAttribute("var", sessionVar);
		}
		ModelMap model = new ModelMap("message", Message.MESSAGE)
				.addAttribute("sessionVar", sessionVar);
//		System.out.println(">>>");
		return new ModelAndView("hello", model);
	}

}