package com.springBoot_firstExample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import domain.HelloService;
import domain.HelloServiceImpl;

@SpringBootApplication
public class SpringBootFirstExampleApplication {

    public static void main(String[] args) {
	SpringApplication.run(SpringBootFirstExampleApplication.class, args);
    }

    @Bean
    HelloService helloService() {
	return new HelloServiceImpl();
    }
}
