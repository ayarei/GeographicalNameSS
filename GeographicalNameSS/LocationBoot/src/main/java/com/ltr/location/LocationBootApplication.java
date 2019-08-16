package com.ltr.location;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

@SpringBootApplication
@EnableDubbo
/**
 * 
 * 邮件发送服务需要在properties文件配置邮箱
 * 
 * 并开启邮箱的smtp服务，设置邮箱安全码
 *
 */
public class LocationBootApplication extends SpringBootServletInitializer{
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LocationBootApplication.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(LocationBootApplication.class, args);
	}
}
