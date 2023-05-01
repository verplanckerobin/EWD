package com.springBoot.bibliotheek;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import domain.Boek;
import service.BoekService;

@RestController
@RequestMapping(value = "/rest")
public class BoekRestController {

    @Autowired
    private BoekService boekService;

    @GetMapping(value = "/boek/isbn/{isbn}")
    public Boek getBoekByISBN(@PathVariable("isbn") String isbn) {
	return boekService.getBoekByISBN(isbn);
    }

    @GetMapping(value = "/boek/auteur/{auteur}")
    public List<Boek> getBoekenByAuteur(@PathVariable("auteur") String auteurNaam) {
	return boekService.getBoekenByAuteur(auteurNaam);
    }
}
