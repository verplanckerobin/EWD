package domain;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor()
@AllArgsConstructor
@EqualsAndHashCode(exclude = { "id", "naam", "aankoopprijs", "aantalSterren", "auteurs", "locaties" })
@ToString(exclude = "id")
public class Boek implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String naam;

    private int isbnNummer;

    @NotNull
    private int aankoopprijs;

    private int aantalSterren;

    private List<Auteur> auteurs;

    private List<Locatie> locaties;

    public Boek(String naam, List<Auteur> auteurs, int isbnNummer, int aankoopprijs, List<Locatie> locaties) {
	this.naam = naam;
	this.auteurs = auteurs;
	this.isbnNummer = isbnNummer;
	this.aankoopprijs = aankoopprijs;
	this.locaties = locaties;
    }
}
