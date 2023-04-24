package com.springBoot.bibliotheek;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import repository.BoekRepository;

@Controller
@RequestMapping("/populairste-boeken")
public class RankingController {

    @Autowired
    private BoekRepository boekRepo;

    @GetMapping("/populairste-boeken")
    public String toonMeestPopulaireBoeken(Model model, Authentication authentication) {
	List<String> listRoles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
	model.addAttribute("userListRoles", listRoles);
	model.addAttribute("lijstPopulairsteBoeken", boekRepo.findAllByOrderByAantalSterrenDescNaamAsc());
	return "populairste-boeken";
    }
}
