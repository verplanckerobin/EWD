package repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import domain.Boek;

public interface BoekRepository extends CrudRepository<Boek, Long> {

    @Query("SELECT b FROM Boek b WHERE b.isbnNummer = :isbnNummer")
    public Boek findByIsbnNummer(@Param("isbnNummer") int isbnNummer);

    @Query("SELECT count(*) FROM Gebruiker g JOIN g.favorieten f ON f.id = :id")
    public int getTotalStart(@Param("id") long id);

    List<Boek> findByBoekNaam(String naam);

    List<Boek> findAllByOrderByAantalSterrenDescBoekNaamAsc();
}
