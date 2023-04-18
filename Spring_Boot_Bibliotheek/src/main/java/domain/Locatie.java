package domain;

import java.io.Serializable;

import org.hibernate.validator.constraints.Range;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class Locatie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean inGebruik;

    @NotNull(message = "{validation.locatiePlaatscode.NotNull.message}")
    @Range(min = 50, max = 300, message = "{validation.locatiePlaatscode.Range.message}")
    private Integer plaatscode1;

    @NotNull(message = "{validation.locatiePlaatscode.NotNull.message}")
    @Range(min = 50, max = 300, message = "{validation.locatiePlaatscode.Range.message}")
    private Integer plaatscode2;

    @NotNull(message = "{validation.locatiePlaatsnaam.NotNull.message}")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "{validation.locatiePlaatsnaam.Pattern.message}")
    private String plaatsnaam;

    public Locatie(Integer plaatscode1, Integer plaatscode2, String plaatsnaam) {
	this.plaatscode1 = plaatscode1;
	this.plaatscode2 = plaatscode2;
	this.plaatsnaam = plaatsnaam;
	inGebruik = false;
    }

}
