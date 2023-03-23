package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import configuration.BeanConfiguration;
import domain.GreetingService;

@SpringJUnitConfig(BeanConfiguration.class)
public class GreetingServiceTest {

    @Autowired
    private GreetingService greeting;

    @Test
    public void test() {
	Assertions.assertEquals("Buenos Dias!", greeting.sayGreeting());
    }
}
