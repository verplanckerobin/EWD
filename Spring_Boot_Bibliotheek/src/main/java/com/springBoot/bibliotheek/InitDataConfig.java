package com.springBoot.bibliotheek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import domain.Auteur;
import domain.Authorities;
import domain.Boek;
import domain.Gebruiker;
import domain.Locatie;
import repository.AuteurRepository;
import repository.BoekRepository;
import repository.GebruikerRepository;
import repository.LocatieRepository;

@Component
public class InitDataConfig implements CommandLineRunner {

    @Autowired
    private GebruikerRepository gebruikerRepo;

    @Autowired
    private BoekRepository boekRepo;

    @Autowired
    private AuteurRepository auteurRepo;

    @Autowired
    private LocatieRepository locatieRepo;

    @Override
    public void run(String... args) throws Exception {
	Gebruiker admin = new Gebruiker("admin", "$2y$10$xT2EKeAP.Ey84iy5dOwuOe5hxtRhvGVk6aLIpgAIpAzzu8xfJWPpO",
		Authorities.ROLE_ADMIN, true, 0);
	Gebruiker user1 = new Gebruiker("user1", "$2y$10$E.442wS/c9QXLpkLcXaOY.Bet9jTm/aoOUi65yvtuvmJuBJJu1KcG",
		Authorities.ROLE_USER, true, 3);
	Gebruiker user2 = new Gebruiker("user2", "$2y$10$E.442wS/c9QXLpkLcXaOY.Bet9jTm/aoOUi65yvtuvmJuBJJu1KcG",
		Authorities.ROLE_USER, true, 3);

	gebruikerRepo.save(admin);
	gebruikerRepo.save(user1);
	gebruikerRepo.save(user2);

	Auteur auteur1 = new Auteur("Rowling", "J. K.");
	Auteur auteur2 = new Auteur("Tolkien", "J. R. R.");
	Auteur auteur3 = new Auteur("Martin", "George R. R.");

	Locatie l1 = new Locatie(50, 100, "Brugge");
	Locatie l2 = new Locatie(51, 101, "Brugge");
	Locatie l3 = new Locatie(52, 102, "Brugge");
	Locatie l4 = new Locatie(53, 103, "Brugge");
	Locatie l5 = new Locatie(54, 104, "Brugge");
	Locatie l6 = new Locatie(55, 105, "Brugge");
	Locatie l7 = new Locatie(56, 106, "Brugge");

	Boek boek1 = new Boek("Harry Potter and the Philosopher's Stone", "9780747532699", 29.99);
	boek1.voegAuteurToe(auteur1);
	boek1.voegLocatieToe(l1);

	Boek boek2 = new Boek("Harry Potter and the Chamber of Secrets", "9780822579496", 29.99);
	boek2.voegAuteurToe(auteur1);
	boek2.voegLocatieToe(l2);

	Boek boek3 = new Boek("The Fellowship of the Ring", "9780261104013", 32.99);
	boek3.voegAuteurToe(auteur2);
	boek3.voegLocatieToe(l3);
	boek3.voegLocatieToe(l4);

	Boek boek4 = new Boek("The Two Towers", "9780345339713", 25.99);
	boek4.voegAuteurToe(auteur2);
	boek4.voegLocatieToe(l5);

	Boek boek5 = new Boek("A Game of Thrones", "9780553808049", 27.99);
	boek5.voegAuteurToe(auteur3);
	boek5.voegLocatieToe(l6);

	Boek boek6 = new Boek("A Storm of Swords", "9780593158951", 28.99);
	boek6.voegAuteurToe(auteur3);
	boek6.voegLocatieToe(l7);

	auteurRepo.save(auteur1);
	auteurRepo.save(auteur2);
	auteurRepo.save(auteur3);

	locatieRepo.save(l1);
	locatieRepo.save(l2);
	locatieRepo.save(l3);
	locatieRepo.save(l4);
	locatieRepo.save(l5);
	locatieRepo.save(l6);
	locatieRepo.save(l7);

	boekRepo.save(boek1);
	boekRepo.save(boek2);
	boekRepo.save(boek3);
	boekRepo.save(boek4);
	boekRepo.save(boek5);
	boekRepo.save(boek6);
    }
}
