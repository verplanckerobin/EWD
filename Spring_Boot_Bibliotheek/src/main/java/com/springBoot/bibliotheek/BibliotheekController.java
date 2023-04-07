package com.springBoot.bibliotheek;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.Boek;
import repository.BoekRepository;

@Controller
@RequestMapping("/bibliotheek")
public class BibliotheekController {

    @Autowired
    private BoekRepository boekRepo;

    @GetMapping
    public String getBibliotheek(Model model, Authentication authentication) {
	List<String> listRoles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
	model.addAttribute("username", authentication.getName());
	model.addAttribute("userListRoles", listRoles);
	model.addAttribute("boekList", boekRepo.findAll());
	return "bibliotheek";
    }

    @GetMapping("/boek/{id}")
    public String getBoek(@PathVariable Long id, Model model) {
	Optional<Boek> boek = boekRepo.findById(id);
	if (boek == null) {
	    return "redirect:/bibliotheek";
	}
	if (boek.isPresent()) {
	    model.addAttribute("boek", boek.get());
	}
	return "boekDetail";
    }

    @GetMapping("/voegBoekToe")
    public String toonVoegBoekToeForm(Model model) {
	return "voegBoekToe";
    }

//    @PostMapping("/voegBoekToe")
//    public String voegBoekToe(Boek boek) {
//
//    }

}
