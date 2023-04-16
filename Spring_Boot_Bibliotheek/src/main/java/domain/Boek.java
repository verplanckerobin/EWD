package domain;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
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
@EqualsAndHashCode(exclude = { "naam", "aankoopprijs", "aantalSterren", "auteurs", "locaties" })
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "boek_auteur", joinColumns = @JoinColumn(name = "boek_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "auteur_id", referencedColumnName = "id"))
    private List<Auteur> auteurs;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "boek_id", referencedColumnName = "id")
    private List<Locatie> locaties;
}
