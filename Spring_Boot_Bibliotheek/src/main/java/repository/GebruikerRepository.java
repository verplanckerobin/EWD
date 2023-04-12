package repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import domain.Gebruiker;

public interface GebruikerRepository extends CrudRepository<Gebruiker, Long> {

    @Query("SELECT g FROM Gebruiker g WHERE g.username = :username")
    public Gebruiker getGebruikerByUsername(@Param("username") String username);
}
