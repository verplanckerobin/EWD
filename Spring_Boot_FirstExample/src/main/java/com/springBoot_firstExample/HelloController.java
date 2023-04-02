package com.springBoot_firstExample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import domain.HelloService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j // nodig voor logging
public class HelloController {

    @Autowired
    private HelloService helloService;

    @GetMapping("/hello")
    public String showFormPage(Model model) {
	model.addAttribute("name", new Name()); // th:object="${name}" in HTML verwijst naar deze "name"
	log.info("get hello");
	return "nameForm"; // verwijst naar HTML file
    }

    @PostMapping("/hello")
    public String onSubmit(Name name, Model model) {
	log.info("post hello, Name submitted: {}", name != null ? name.getValue() : "null");
	log.info("An INFO Message");
	log.warn("A Warn Message");
	log.error("An ERROR Message");

	log.debug("inside hello post method");

	model.addAttribute("helloMessage", helloService.sayHello(name.getValue()));
	return "helloView";
    }
}
