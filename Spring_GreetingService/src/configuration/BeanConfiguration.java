package configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import domain.GreetingService;
import domain.GreetingServiceImpl;

@Configuration
public class BeanConfiguration {

    @Bean
    GreetingService greetingService() {
	GreetingServiceImpl greeting = new GreetingServiceImpl();
	greeting.setGreeting("Buenos Dias!");
	return greeting;
    }
}
