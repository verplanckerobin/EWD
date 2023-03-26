package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import configuration.BeanConfiguration;
import domain.MindReader;
import domain.Thinker;

@SpringJUnitConfig(BeanConfiguration.class)
class MagicianVolunteerTest {

    @Autowired
    private Thinker volunteer;

    @Autowired
    private MindReader magician;

    @Test
    public void magicianShouldReadVolunteersMind() {
	volunteer.thinkOfSomething("Queen of Hearts");
	Assertions.assertEquals("Queen of Hearts", magician.getThoughts());
    }

}
