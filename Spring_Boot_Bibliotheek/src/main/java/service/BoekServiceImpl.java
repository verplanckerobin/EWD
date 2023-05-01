package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import domain.Auteur;
import domain.Boek;
import exceptions.AuteurNotFoundException;
import exceptions.BoekNotFoundException;
import repository.AuteurRepository;
import repository.BoekRepository;

public class BoekServiceImpl implements BoekService {

    @Autowired
    private BoekRepository boekRepo;

    @Autowired
    private AuteurRepository auteurRepo;

    @Override
    public Boek getBoekByISBN(String isbnNummer) {
	Boek boek = boekRepo.findByIsbnNummer(isbnNummer);
	if (boek == null) {
	    throw new BoekNotFoundException(isbnNummer);
	}
	return boek;
    }

    @Override
    public List<Boek> getBoekenByAuteur(String auteurNaam) {
	Auteur auteur = auteurRepo.findByAuteurNaam(auteurNaam);
	if (auteur == null) {
	    throw new AuteurNotFoundException(auteurNaam);
	}
	return boekRepo.findByAuteursContains(auteur);
    }
}
