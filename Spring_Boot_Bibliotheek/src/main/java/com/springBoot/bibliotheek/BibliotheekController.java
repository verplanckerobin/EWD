package com.springBoot.bibliotheek;

import java.security.Principal;
import java.util.List;

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
import domain.Gebruiker;
import domain.Locatie;
import jakarta.validation.Valid;
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
    public String getBoek(@PathVariable Long id, Model model, Principal principal) {
	Boek boekFetch = boekRepo.findById(id).get();
	Gebruiker actieveGebruiker = gebruikerRepo.getGebruikerByUsername(principal.getName());
	Boolean heeftMaxAantalFavorieten = false;

	if (boekFetch == null) {
	    return "redirect:/bibliotheek";
	}

	if (actieveGebruiker.getFavorieten().size() == actieveGebruiker.getMaxAantalFavorieten()) {
	    heeftMaxAantalFavorieten = true;
	}

	model.addAttribute("boek", boekFetch);
	model.addAttribute("lijstAuteurs", boekFetch.getAuteurs());
	model.addAttribute("lijstLocaties", boekFetch.getLocaties());
	model.addAttribute("isReedsFavoriet", actieveGebruiker.getFavorieten().contains(boekFetch));
	model.addAttribute("maxAantal", heeftMaxAantalFavorieten);

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

    @PostMapping("/voeg-boek-toe")
    public String voegBoekToe(@Valid @ModelAttribute("boek") Boek boek, BindingResult result,
	    @ModelAttribute("auteur") Auteur auteur, @ModelAttribute("locatie") Locatie locatie) {
	if (result.hasErrors()) {
	    return "voeg-boek-toe";
	}
	boekRepo.save(boek);
	return "redirect:/bibliotheek";
    }

    @PostMapping("/voeg-auteur-toe")
    public String voegAuteurToe(@ModelAttribute("auteur") Auteur auteur, BindingResult result, Model model) {
	if (result.hasErrors()) {
	    return "voeg-boek-toe";
	}
	auteurRepo.save(auteur);
	return "redirect:/voeg-boek-toe";
    }

    @PostMapping("/voeg-locatie-toe")
    public String voegLocatieToe(@ModelAttribute("locatie") Locatie locatie, BindingResult result, Model model) {
	if (result.hasErrors()) {
	    return "voeg-boek-toe";
	}
	locatieRepo.save(locatie);
	return "redirect:/voeg-boek-toe";
    }

    @PostMapping("/boek/{id}")
    public String saveBoekFavoriet(@PathVariable(value = "id") Long id, Principal principal) {
	Gebruiker actieveGebruiker = gebruikerRepo.getGebruikerByUsername(principal.getName());
	Boek boekFetch = boekRepo.findById(id).get();

	if (actieveGebruiker.getFavorieten().contains(boekFetch)) {
	    actieveGebruiker.removeFavoriet(boekFetch);
	    boekFetch.setAantalSterren(boekFetch.getAantalSterren() - 1);
	    boekRepo.save(boekFetch);
	    gebruikerRepo.save(actieveGebruiker);
	} else {
	    actieveGebruiker.addFavoriet(boekFetch);
	    boekFetch.setAantalSterren(boekFetch.getAantalSterren() + 1);
	    boekRepo.save(boekFetch);
	    gebruikerRepo.save(actieveGebruiker);
	}
	return "redirect:/bibliotheek";
    }
}
