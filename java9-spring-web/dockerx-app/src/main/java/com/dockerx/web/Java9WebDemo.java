package com.dockerx.web;

import com.dockerx.admin.configration.ServiceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2017/11/15 19:19.
 */
@SpringBootApplication
@ComponentScan("com.dockerx.web.*")
@Import(ServiceConfiguration.class)
public class Java9WebDemo {
    public static void main(String[] args) {
        SpringApplication.run(Java9WebDemo.class,args);
    }
}
