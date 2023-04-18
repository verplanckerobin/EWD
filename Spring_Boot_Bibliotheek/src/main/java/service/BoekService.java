package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import domain.Auteur;
import domain.Boek;
import repository.AuteurRepository;
import repository.BoekRepository;

@Service
public class BoekService {

    private final BoekRepository boekRepo;
    private final AuteurRepository auteurRepo;

    public BoekService(BoekRepository boekRepo, AuteurRepository auteurRepo) {
	this.boekRepo = boekRepo;
	this.auteurRepo = auteurRepo;
    }

    public List<Boek> getAllBooks() {
	return boekRepo.findAll();
    }

    public Optional<Boek> getBookById(Long id) {
	return boekRepo.findById(id);
    }

    public Boek saveBook(Boek boek) {
	return boekRepo.save(boek);
    }

    public List<Auteur> getAuthorsByBookId(Long id) {
	Optional<Boek> boek = boekRepo.findById(id);
	if (boek.isPresent()) {
	    return boek.get().getAuteurs();
	} else {
	    return new ArrayList<>();
	}
    }

    public List<Boek> getBooksByAuthorId(Long id) {
	Optional<Auteur> author = auteurRepo.findById(id);
	if (author.isPresent()) {
	    return author.get().getBoeken();
	} else {
	    return new ArrayList<>();
	}
    }

    public void addAuthorToBook(Long bookId, Long authorId) {
	Optional<Boek> boek = boekRepo.findById(bookId);
	Optional<Auteur> auteur = auteurRepo.findById(authorId);
	if (boek.isPresent() && auteur.isPresent()) {
	    List<Auteur> auteurs = boek.get().getAuteurs();
	    if (auteurs.size() < 3) {
		auteurs.add(auteur.get());
		boekRepo.save(boek.get());
	    } else {
		throw new RuntimeException("Book can have maximum of 3 authors");
	    }
	} else {
	    throw new RuntimeException("Book or author not found");
	}
    }
}
