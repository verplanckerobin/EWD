package repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import domain.Auteur;
import domain.Boek;

public interface BoekRepository extends CrudRepository<Boek, Long> {

    Boek findByIsbnNummer(String isbnNummer);

    List<Boek> findByNaam(String naam);

    List<Boek> findAllByOrderByAantalSterrenDescNaamAsc();

    List<Boek> findByAuteursContains(Auteur auteur);

    List<Boek> findAllByOrderByNaamAsc();
}
