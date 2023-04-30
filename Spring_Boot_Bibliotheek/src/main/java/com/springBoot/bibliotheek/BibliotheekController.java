package com.springBoot.bibliotheek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import repository.BoekRepository;

@Controller
@RequestMapping("/bibliotheek")
public class BibliotheekController {

    @Autowired
    private BoekRepository boekRepo;

    @GetMapping
    public String getBibliotheek(Model model, Authentication authentication) {
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	model.addAttribute("userRole", auth.getAuthorities());
	model.addAttribute("username", auth.getName());
	model.addAttribute("boekList", boekRepo.findAll());
	return "bibliotheekOverzicht";
    }
}
