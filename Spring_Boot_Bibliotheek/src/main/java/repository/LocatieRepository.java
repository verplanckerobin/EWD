package repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import domain.Locatie;

public interface LocatieRepository extends CrudRepository<Locatie, Long> {

    Locatie findByPlaatscode1AndPlaatscode2AndPlaatsnaam(int plaatscode1, int plaatscode2, String plaatsnaam);

    @Query("SELECT l FROM Locatie l WHERE l.inGebruik = FALSE")
    List<Locatie> findAllNotInUse();

}
