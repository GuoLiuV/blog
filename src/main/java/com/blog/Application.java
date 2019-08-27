package com.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ServletComponentScan
@EnableScheduling
@SpringBootApplication
@MapperScan("com.blog.dao")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
        System.out.println("<-----------------启动成功----------------->");
        System.out.println("<-----------------启动成功----------------->");
        System.out.println("<-----------------启动成功----------------->");
    }
}
