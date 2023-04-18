package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Auteur;

@Repository
public interface AuteurRepository extends JpaRepository<Auteur, Long> {

    Auteur findByAuteurNaamAndVoornaam(String naam, String voornaam);

}
