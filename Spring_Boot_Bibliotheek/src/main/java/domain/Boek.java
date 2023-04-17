package domain;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.Range;

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
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor()
@AllArgsConstructor
@EqualsAndHashCode(exclude = { "boekNaam", "aankoopprijs", "aantalSterren", "auteurs", "locaties" })
public class Boek implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "{validation.boekNaam.NotEmpty.message}")
    private String boekNaam;

    @NotNull(message = "{validation.boekISBN.NotNull.message}")
    @Pattern(regexp = "^\\d{13}$", message = "{validation.boekISBN.Pattern.message}")
    @ISBN(message = "{validation.boekISBN.Validation.message}")
    private String isbnNummer;

    @Range(min = 1, max = 99, message = "{validation.boekAankoopprijs.Range.message}")
    private Integer aankoopprijs;

    private int aantalSterren;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "boek_auteur", joinColumns = @JoinColumn(name = "boek_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "auteur_id", referencedColumnName = "id"))
    @NotNull(message = "{validation.auteurs.MinSize.message}")
    @Size(min = 1, message = "{validation.auteurs.MinSize.message}")
    @Size(max = 3, message = "{validation.auteurs.MaxSize.message}")
    private List<Auteur> auteurs;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "boek_id", referencedColumnName = "id")
    @NotNull(message = "{validation.locaties.MinSize.message}")
    @Size(min = 1, message = "{validation.locaties.MinSize.message}")
    @Size(max = 3, message = "{validation.locaties.MaxSize.message}")
    private List<Locatie> locaties;
}
