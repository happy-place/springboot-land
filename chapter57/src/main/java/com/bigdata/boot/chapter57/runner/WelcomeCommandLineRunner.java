package com.bigdata.boot.chapter57.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @ClassName WelcomeCommandLineRunner
 * @Description TODO
 * @Author HuHao（huhao1@cmcm.com）
 * @Date 2020/7/1 07:51
 * @Version 1.0
 **/
@Component
public class WelcomeCommandLineRunner  implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("***** WELCOME TO THE DEMO *****");
    }
}
