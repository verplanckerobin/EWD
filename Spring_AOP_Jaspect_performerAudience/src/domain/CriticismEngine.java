package domain;

import java.util.Random;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CriticismEngine {

    @Pointcut("execution(* *.perform(..))")
    private void performance() {
    }

    private String[] criticism;

    public void setCriticism(String[] criticism) {
	this.criticism = criticism;
    }

    @After("performance()")
    public void giveCriticism() {
	int random = new Random().nextInt(criticism.length);
	System.out.println(criticism[random]);
    }

}