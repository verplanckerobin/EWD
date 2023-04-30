package com.springBoot.bibliotheek;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import domain.Auteur;
import domain.Boek;
import domain.Locatie;
import jakarta.validation.Valid;
import repository.AuteurRepository;
import repository.BoekRepository;
import repository.LocatieRepository;

@Controller
public class AdminController {

    @Autowired
    private BoekRepository boekRepo;

    @Autowired
    private AuteurRepository auteurRepo;

    @Autowired
    private LocatieRepository locatieRepo;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/voeg-boek-toe")
    public String toonVoegBoekToeForm(Model model, Authentication authentication) {
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	model.addAttribute("boek", new Boek());
	model.addAttribute("auteur", new Auteur());
	model.addAttribute("locatie", new Locatie());
	model.addAttribute("auteurs", auteurRepo.findAll());
	model.addAttribute("locaties", locatieRepo.findAllNotInUse());
	model.addAttribute("userRole", auth.getAuthorities());
	return "voegBoekToe";
    }

    @PostMapping("/voeg-boek-toe")
    public String voegBoekToe(@Valid @ModelAttribute("boek") Boek boek, BindingResult result, Model model,
	    Locale locale) {
	Boek bestaandBoek = boekRepo.findByIsbnNummer(boek.getIsbnNummer());

	if (result.hasErrors() || bestaandBoek != null) {
	    if (bestaandBoek != null) {
		model.addAttribute("errorBoekBestaat",
			messageSource.getMessage("validation.boek.Exists.message", null, locale));
	    }
	    model.addAttribute("auteur", new Auteur());
	    model.addAttribute("locatie", new Locatie());
	    model.addAttribute("auteurs", auteurRepo.findAll());
	    model.addAttribute("locaties", locatieRepo.findAllNotInUse());
	    return "voegBoekToe";
	}

	boek.getLocaties().forEach(l -> l.setInGebruik(true));
	boekRepo.save(boek);
	return "redirect:/bibliotheek";
    }

    @PostMapping("voeg-auteur-toe")
    public String voegAuteurToe(@Valid @ModelAttribute("auteur") Auteur auteur, BindingResult result, Model model,
	    Locale locale) {
	Auteur bestaandeAuteur = auteurRepo.findByAuteurNaamAndVoornaam(auteur.getAuteurNaam(), auteur.getVoornaam());

	if (result.hasErrors() || bestaandeAuteur != null) {
	    if (bestaandeAuteur != null) {
		model.addAttribute("errorAuteurBestaat",
			messageSource.getMessage("validation.auteur.Exists.message", null, locale));
	    }
	    model.addAttribute("boek", new Boek());
	    model.addAttribute("locatie", new Locatie());
	    model.addAttribute("auteurs", auteurRepo.findAll());
	    model.addAttribute("locaties", locatieRepo.findAllNotInUse());
	    return "voegBoekToe";
	}

	auteurRepo.save(auteur);
	return "redirect:/voeg-boek-toe";
    }

    @PostMapping("voeg-locatie-toe")
    public String voegLocatieToe(@Valid @ModelAttribute("locatie") Locatie locatie, BindingResult result, Model model,
	    Locale locale) {
	Locatie bestaandeLocatie = locatieRepo.findByPlaatscode1AndPlaatscode2AndPlaatsnaam(locatie.getPlaatscode1(),
		locatie.getPlaatscode2(), locatie.getPlaatsnaam());

	int verschil = Math.abs(locatie.getPlaatscode1() - locatie.getPlaatscode2());

	if (result.hasErrors() || bestaandeLocatie != null || verschil < 50) {
	    if (locatieRepo.findByPlaatscode1AndPlaatscode2AndPlaatsnaam(locatie.getPlaatscode1(),
		    locatie.getPlaatscode2(), locatie.getPlaatsnaam()) != null) {
		model.addAttribute("errorLocatieBestaat",
			messageSource.getMessage("validation.locatie.Exists.message", null, locale));
	    }

	    if (verschil < 50) {
		model.addAttribute("errorVerschilCodes",
			messageSource.getMessage("validation.locatie.Verschil.message", null, locale));
	    }
	    model.addAttribute("boek", new Boek());
	    model.addAttribute("auteur", new Auteur());
	    model.addAttribute("auteurs", auteurRepo.findAll());
	    model.addAttribute("locaties", locatieRepo.findAllNotInUse());
	    return "voegBoekToe";
	}

	locatieRepo.save(locatie);
	return "redirect:/voeg-boek-toe";
    }

}
