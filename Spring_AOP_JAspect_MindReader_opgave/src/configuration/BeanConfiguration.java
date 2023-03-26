package configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import domain.Magician;
import domain.Volunteer;

@Configuration
@EnableAspectJAutoProxy
public class BeanConfiguration {

    @Bean
    Magician magician() {
	return new Magician();
    }

    @Bean
    Volunteer volunteer() {
	return new Volunteer();
    }

}