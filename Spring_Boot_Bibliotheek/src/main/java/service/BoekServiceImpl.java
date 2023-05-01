package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import domain.Auteur;
import domain.Boek;
import repository.AuteurRepository;
import repository.BoekRepository;

public class BoekServiceImpl implements BoekService {

    @Autowired
    private BoekRepository boekRepo;

    @Autowired
    private AuteurRepository auteurRepo;

    @Override
    public Boek getBoekByISBN(String isbnNummer) {
	return boekRepo.findByIsbnNummer(isbnNummer);
    }

    @Override
    public List<Boek> getBoekenByAuteur(String auteurNaam) {
	Auteur auteur = auteurRepo.findByAuteurNaam(auteurNaam);
	return boekRepo.findByAuteursContains(auteur);
    }
}
