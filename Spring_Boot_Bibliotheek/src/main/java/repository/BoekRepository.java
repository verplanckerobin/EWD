package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import domain.Boek;

@Repository
public interface BoekRepository extends JpaRepository<Boek, Long> {

    @Query("SELECT count(*) FROM Gebruiker g JOIN g.favorieten f ON f.id = :id")
    public int getTotalStart(@Param("id") long id);

    Boek findByIsbnNummer(String isbnNummer);

    List<Boek> findByNaam(String naam);

    List<Boek> findAllByOrderByAantalSterrenDescNaamAsc();
}
