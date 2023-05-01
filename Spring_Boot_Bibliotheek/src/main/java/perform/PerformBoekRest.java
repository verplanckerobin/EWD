package perform;

import org.springframework.web.reactive.function.client.WebClient;

import domain.Boek;
import reactor.core.publisher.Mono;

public class PerformBoekRest {

    private final String SERVER_URI = "http://localhost:8080/rest";
    private WebClient webClient = WebClient.create();

    public PerformBoekRest() throws Exception {
	System.out.println("\n----------------------- GET BOEK BY ISBN -----------------------");
	System.out.println("----------------------------------------------------------------");
	getBoekByIsbn("9780261104013");
	System.out.println("\n-------------------- GET BOEKEN BY AUTEUR ----------------------");
	System.out.println("----------------------------------------------------------------");
	getBoekenByAuteur("Rowling");
    }

    private void getBook(String uri) {
	webClient.get().uri(uri).retrieve().bodyToMono(Boek.class).doOnSuccess(boek -> printBoekData(boek)).block();
    }

    private void getBoekByIsbn(String isbn) {
	getBook(SERVER_URI + "/boek/isbn/" + isbn);
    }

    private void printBoekData(Boek boek) {
	StringBuilder listAuteurs = new StringBuilder();
	StringBuilder listLocaties = new StringBuilder();
	boek.getAuteurs().forEach(auteur -> {
	    listAuteurs.append(auteur.getAuteurNaam() + " " + auteur.getVoornaam());
	    if (boek.getAuteurs().indexOf(auteur) != boek.getAuteurs().size() - 1) {
		listLocaties.append(" | ");
	    }
	});
	boek.getLocaties().forEach(locatie -> {
	    listLocaties.append(
		    locatie.getPlaatscode1() + " - " + locatie.getPlaatscode2() + " - " + locatie.getPlaatsnaam());
	    if (boek.getLocaties().indexOf(locatie) != boek.getLocaties().size() - 1) {
		listLocaties.append(" | ");
	    }
	});
	System.out.printf(
		"Boek naam: %s%n" + "ISBN nummer: %s%n" + "Aankoopprijs: €%s%n" + "Auteurs: %s%n" + "Locaties: %s",
		boek.getNaam(), boek.getIsbnNummer(), boek.getAankoopprijs(), listAuteurs, listLocaties);
    }

    private void getBoeken(String uri) {
	webClient.get().uri(uri).retrieve().bodyToFlux(Boek.class).flatMap(boek -> {
	    printBoekenData(boek);
	    return Mono.empty();
	}).blockLast();
    }

    private void getBoekenByAuteur(String auteurNaam) {
	getBoeken(SERVER_URI + "/boek/auteur/" + auteurNaam);
    }

    private void printBoekenData(Boek boek) {
	System.out.printf("ID: %s, Name: %s%n", boek.getId(), boek.getNaam());
    }
}
