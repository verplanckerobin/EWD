package service;

import java.util.List;

import domain.Boek;

public interface BoekService {

    public Boek getBoekByISBN(String isbnNummer);

    public List<Boek> getBoekenByAuteur(String auteurNaam);

}
