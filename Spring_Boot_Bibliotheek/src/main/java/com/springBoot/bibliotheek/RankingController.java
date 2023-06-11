package com.springBoot.bibliotheek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import repository.BoekRepository;

// Controller voor populairste boeken
@Controller
@RequestMapping("/populairste-boeken")
public class RankingController {

    @Autowired
    private BoekRepository boekRepo;

    @GetMapping
    public String toonMeestPopulaireBoeken(Model model, Authentication authentication) {
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	model.addAttribute("userRole", auth.getAuthorities());
	model.addAttribute("lijstPopulairsteBoeken", boekRepo.findAllByOrderByAantalSterrenDescNaamAsc());
	return "populairste-boeken";
    }
}
