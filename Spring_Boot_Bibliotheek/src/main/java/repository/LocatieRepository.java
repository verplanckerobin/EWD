package repository;

import org.springframework.data.repository.CrudRepository;

import domain.Locatie;

public interface LocatieRepository extends CrudRepository<Locatie, Long> {

    Locatie findByPlaatscode1AndPlaatscode2AndPlaatsnaam(int plaatscode1, int plaatscode2, String plaatsnaam);

}
