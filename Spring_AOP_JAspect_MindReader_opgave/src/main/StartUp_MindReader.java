package main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import configuration.BeanConfiguration;
import domain.MindReader;
import domain.Thinker;

public class StartUp_MindReader {

    public static void main(String args[]) {

	try (var context = new AnnotationConfigApplicationContext(BeanConfiguration.class)) {

	    var thinker = context.getBean("volunteer", Thinker.class);

	    var mindReader = context.getBean("magician", MindReader.class);

	    thinker.thinkOfSomething("Queen of Hearts");

	    System.out.println(mindReader.getThoughts());
	}
    }
}