package domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import validator.ISBNValidator;
import validator.PrijsValidator;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = { "id", "naam", "aankoopprijs", "aantalSterren", "auteurs", "locaties" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "boek_id")
public class Boek implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("boek_id")
    private Long id;

    @NotEmpty(message = "{validation.boekNaam.NotEmpty.message}")
    private String naam;

    @ISBNValidator
    private String isbnNummer;

    @NumberFormat(style = Style.CURRENCY)
    @PrijsValidator
    private BigDecimal aankoopprijs;

    private int aantalSterren;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "boek_auteur", joinColumns = @JoinColumn(name = "boek_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "auteur_id", referencedColumnName = "id"))
    @NotNull(message = "{validation.auteurs.MinSize.message}")
    @Size(min = 1, message = "{validation.auteurs.MinSize.message}")
    @Size(max = 3, message = "{validation.auteurs.MaxSize.message}")
    private List<Auteur> auteurs = new ArrayList<>();

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "boek_id", referencedColumnName = "id")
    @NotNull(message = "{validation.locaties.MinSize.message}")
    @Size(min = 1, message = "{validation.locaties.MinSize.message}")
    @Size(max = 3, message = "{validation.locaties.MaxSize.message}")
    private List<Locatie> locaties = new ArrayList<>();

    public Boek(String naam, String isbnNummer, BigDecimal aankoopprijs) {
	this.naam = naam;
	setIsbnNummer(isbnNummer);
	this.aankoopprijs = aankoopprijs;
	this.aantalSterren = 0;
	auteurs = new ArrayList<>();
	locaties = new ArrayList<>();
    }

    public void setIsbnNummer(String isbnNummer) {
	if (isbnNummer != null) {
	    this.isbnNummer = isbnNummer.trim().replace("-", "").replace(" ", "");
	}
    }

    public void voegAuteurToe(Auteur auteur) {
	if (!auteurs.contains(auteur)) {
	    auteurs.add(auteur);
	}
    }

    public void voegLocatieToe(Locatie locatie) {
	if (locatie != null) {
	    locatie.setInGebruik(true);
	    locaties.add(locatie);
	}
    }
}
