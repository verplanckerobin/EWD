package com.springBoot.bibliotheek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import domain.Authorities;
import domain.Gebruiker;
import repository.GebruikerRepository;

@Component
public class InitDataConfig implements CommandLineRunner {

    @Autowired
    private GebruikerRepository gebruikerRepo;

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
    }
}
