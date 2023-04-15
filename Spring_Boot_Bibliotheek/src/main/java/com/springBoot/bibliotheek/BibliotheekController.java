package com.springBoot.bibliotheek;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import domain.Auteur;
import domain.Boek;
import domain.Gebruiker;
import domain.Locatie;
import repository.AuteurRepository;
import repository.BoekRepository;
import repository.GebruikerRepository;
import repository.LocatieRepository;

@Controller
public class BibliotheekController {

    @Autowired
    private BoekRepository boekRepo;

    @Autowired
    private AuteurRepository auteurRepo;

    @Autowired
    private LocatieRepository locatieRepo;

    @Autowired
    private GebruikerRepository gebruikerRepo;

    @GetMapping("bibliotheek")
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
	    model.addAttribute("lijstAuteurs", boek.get().getAuteurs());
	    model.addAttribute("lijstLocaties", boek.get().getLocaties());
	    model.addAttribute("lijstFavorieten",
		    gebruikerRepo
			    .getGebruikerByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
			    .getFavorieten());
	}
	return "boek-detail";
    }

    @GetMapping("/voeg-boek-toe")
    public String toonVoegBoekToeForm(Model model) {
	model.addAttribute("boek", new Boek());
	model.addAttribute("auteur", new Auteur());
	model.addAttribute("locatie", new Locatie());
	model.addAttribute("auteurList", auteurRepo.findAll());
	model.addAttribute("locatieList", locatieRepo.findAll());
	return "voeg-boek-toe";
    }

    @GetMapping("/populairste-boeken")
    public String toonMeestPopulaireBoeken(Model model) {
	model.addAttribute("lijstPopulairsteBoeken", boekRepo.findAllByOrderByAantalSterrenDescNaamAsc());
	return "populairste-boeken";
    }

    @PostMapping("/voeg-boek-toe/save")
    public String voegBoekToe(@ModelAttribute("boek") Boek boek, BindingResult result, Model model) {
	boekRepo.save(boek);
	return "redirect:/bibliotheek";
    }

    @PostMapping("/voeg-auteur-toe/save")
    public String voegAuteurToe(@ModelAttribute("auteur") Auteur auteur, BindingResult result, Model model) {
	auteurRepo.save(auteur);
	return "redirect:/voeg-boek-toe";
    }

    @PostMapping("/voeg-locatie-toe/save")
    public String voegLocatieToe(@ModelAttribute("locatie") Locatie locatie, BindingResult result, Model model) {
	locatieRepo.save(locatie);
	return "redirect:/voeg-boek-toe";
    }

    @PostMapping("/boek/{id}")
    public String saveBoekFavoriet(@PathVariable(value = "id") Long id) {
	Gebruiker actieveGebruiker = gebruikerRepo
		.getGebruikerByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
	Optional<Boek> boek = boekRepo.findById(id);
	actieveGebruiker.addFavoriet(boek.get());
	boek.get().setAantalSterren(boek.get().getAantalSterren() + 1);
	boekRepo.save(boek.get());
	gebruikerRepo.save(actieveGebruiker);
	return "redirect:/bibliotheek";
    }
}
