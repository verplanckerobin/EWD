package repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import domain.Boek;

public interface BoekRepository extends CrudRepository<Boek, Long> {

    @Query("SELECT b FROM Boek b WHERE b.isbnNummer = :isbnNummer")
    Boek findByIsbnNummer(@Param("isbnNummer") int isbnNummer);
}
