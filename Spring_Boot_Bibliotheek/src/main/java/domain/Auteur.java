package domain;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class Auteur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "{validation.auteurNaam.NotEmpty.message}")
    private String auteurNaam;

    @NotEmpty(message = "{validation.auteurVoornaam.NotEmpty.message}")
    private String voornaam;

//    @ManyToMany(mappedBy = "auteurs", cascade = CascadeType.MERGE)
//    private List<Boek> boeken;

    public Auteur(String auteurNaam, String voornaam) {
	this.auteurNaam = auteurNaam;
	this.voornaam = voornaam;
	// boeken = new ArrayList<>();
    }
}
