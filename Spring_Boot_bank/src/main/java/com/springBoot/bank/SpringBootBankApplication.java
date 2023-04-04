package com.springBoot.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import domain.BankCustomerLookup;

@SpringBootApplication
public class SpringBootBankApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
	SpringApplication.run(SpringBootBankApplication.class, args);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
	registry.addRedirectViewController("/", "/bank");
    }

    @Bean
    BankCustomerLookup bankCustomerLookup() {
	return new BankCustomerLookup();
    }
}
