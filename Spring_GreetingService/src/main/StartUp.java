package main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import configuration.BeanConfiguration;
import domain.GreetingService;

public class StartUp {

    public static void main(String[] args) {

	try (var context = new AnnotationConfigApplicationContext(BeanConfiguration.class)) {
	    var greeting = context.getBean("greetingService", GreetingService.class);
	    System.out.println(greeting.sayGreeting());
	}
    }
}
