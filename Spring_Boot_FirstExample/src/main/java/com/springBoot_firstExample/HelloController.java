package com.springBoot_firstExample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import domain.HelloService;

@Controller
public class HelloController {

    @Autowired
    private HelloService helloService;

    @GetMapping("/hello")
    public String showFormPage(Model model) {
	model.addAttribute("name", new Name()); // th:object="${name}" in HTML verwijst naar deze "name"
	return "nameForm"; // verwijst naar HTML file
    }

    @PostMapping("/hello")
    public String onSubmit(Name name, Model model) {
	model.addAttribute("helloMessage", helloService.sayHello(name.getValue()));
	return "helloView";
    }
}
