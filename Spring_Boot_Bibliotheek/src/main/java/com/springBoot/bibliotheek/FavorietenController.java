package com.springBoot.bibliotheek;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import domain.Boek;
import domain.Gebruiker;
import repository.BoekRepository;
import repository.GebruikerRepository;

@Controller
@RequestMapping("/boek")
public class FavorietenController {

    @Autowired
    private BoekRepository boekRepo;

    @Autowired
    private GebruikerRepository gebruikerRepo;

    @GetMapping("{id}")
    public String getBoek(@PathVariable Long id, Model model, Principal principal, Authentication authentication) {
	Boek boekFetch = boekRepo.findById(id).get();
	Gebruiker actieveGebruiker = gebruikerRepo.getGebruikerByUsername(principal.getName());
	Boolean heeftMaxAantalFavorieten = false;
	List<String> listRoles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

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
	model.addAttribute("userListRoles", listRoles);

	return "boek-detail";
    }

    @PostMapping("{id}")
    public String saveBoekFavoriet(@PathVariable(value = "id") Long id, Principal principal,
	    RedirectAttributes redirectAttributes) {
	Gebruiker actieveGebruiker = gebruikerRepo.getGebruikerByUsername(principal.getName());
	Boek boekFetch = boekRepo.findById(id).get();

	if (actieveGebruiker.getFavorieten().contains(boekFetch)) {
	    actieveGebruiker.removeFavoriet(boekFetch);
	    boekFetch.setAantalSterren(boekFetch.getAantalSterren() - 1);
	    boekRepo.save(boekFetch);
	    gebruikerRepo.save(actieveGebruiker);
	    redirectAttributes.addFlashAttribute("favorietBericht",
		    boekFetch.getNaam() + " werd verwijderd uit favorieten");
	} else {
	    actieveGebruiker.addFavoriet(boekFetch);
	    boekFetch.setAantalSterren(boekFetch.getAantalSterren() + 1);
	    boekRepo.save(boekFetch);
	    gebruikerRepo.save(actieveGebruiker);
	    redirectAttributes.addFlashAttribute("favorietBericht",
		    boekFetch.getNaam() + " werd toegevoegd aan favorieten");
	}
	return "redirect:/bibliotheek";
    }

}
