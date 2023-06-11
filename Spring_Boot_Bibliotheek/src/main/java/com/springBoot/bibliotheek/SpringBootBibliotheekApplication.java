package com.springBoot.bibliotheek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import perform.PerformBoekRest;
import service.BoekService;
import service.BoekServiceImpl;

// Samengevat dient deze klasse als het startpunt voor de Spring Boot applicatie. 
// Het configureert de applicatie, stelt JPA repositories en entity scanning in, 
// definieert view controllers en initialiseert een bean voor de BoekService interface.

@SpringBootApplication
@EnableJpaRepositories("repository")
@EntityScan("domain")
public class SpringBootBibliotheekApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
	SpringApplication.run(SpringBootBibliotheekApplication.class, args);

	try {
	    new PerformBoekRest();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
	registry.addRedirectViewController("/", "/bibliotheek");
	registry.addViewController("/403").setViewName("403");
    }

    @Bean
    BoekService boekService() {
	return new BoekServiceImpl();
    }
}
