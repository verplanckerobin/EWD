package com.springBoot.bibliotheek;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import domain.Auteur;
import domain.Boek;
import domain.Locatie;
import repository.AuteurRepository;
import repository.BoekRepository;
import repository.LocatieRepository;

@Controller
public class BibliotheekController {

    @Autowired
    private BoekRepository boekRepo;

    @Autowired
    private AuteurRepository auteurRepo;

    @Autowired
    private LocatieRepository locatieRepo;

    @GetMapping("bibliotheek")
    public String getBibliotheek(Model model, Authentication authentication) {
	List<String> listRoles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
	model.addAttribute("username", authentication.getName());
	model.addAttribute("userListRoles", listRoles);
	model.addAttribute("boekList", boekRepo.findAll());
	return "bibliotheek";
    }

    @GetMapping("bibliotheek/boek/{id}")
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
	model.addAttribute("boek", new Boek());
	model.addAttribute("auteur", new Auteur());
	model.addAttribute("locatie", new Locatie());
	model.addAttribute("auteurList", auteurRepo.findAll());
	model.addAttribute("locatieList", locatieRepo.findAll());
	return "voegBoekToe";
    }

    @PostMapping("/voegBoekToe/save")
    public String voegBoekToe(@ModelAttribute("boek") Boek boek, BindingResult result, Model model) {
	// Optional<Boek> bestaandBoek = boekRepo.findById(boek.getId());
	boekRepo.save(boek);
	return "redirect:/bibliotheek";
    }

    @PostMapping("/voegAuteurToe/save")
    public String voegAuteurToe(@ModelAttribute("auteur") Auteur auteur, BindingResult result, Model model) {
	auteurRepo.save(auteur);
	return "redirect:/voegBoekToe";
    }

    @PostMapping("/voegLocatieToe/save")
    public String voegLocatieToe(@ModelAttribute("locatie") Locatie locatie, BindingResult result, Model model) {
	locatieRepo.save(locatie);
	return "redirect:/voegBoekToe";
    }

}