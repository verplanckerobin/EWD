package com.springBoot.jpaMysql_2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableJpaRepositories("repository")
@EntityScan("domain")
public class SpringBootJpaMySql2Application implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJpaMySql2Application.class, args);
	}

	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
	   registry.addRedirectViewController("/", "/guest");
    }
}
