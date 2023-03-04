package domein;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(
			name = "Onderhoudsbeurt.opDatum", 
			query = "SELECT o FROM Onderhoudsbeurt o"
					+ "WHERE :datum BETWEEN o.begindatum AND o.einddatum")
})
public class Onderhoudsbeurt implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

    private LocalDate begindatum;
   
    private LocalDate einddatum;

    @ManyToOne
    private Vervoermiddel vervoermiddel;
    
    protected Onderhoudsbeurt() {
    	
    }

    public Onderhoudsbeurt(LocalDate begindatum, LocalDate einddatum, Vervoermiddel vervoermiddel) {
        this.begindatum = begindatum;
        this.einddatum = einddatum;
        this.vervoermiddel = vervoermiddel;
    }

    public LocalDate getBegindatum() {
        return begindatum;
    }

    public void setBegindatum(LocalDate begindatum) {
        this.begindatum = begindatum;
    }

    public LocalDate getEinddatum() {
        return einddatum;
    }

    public void setEinddatum(LocalDate einddatum) {
        this.einddatum = einddatum;
    }

    public Vervoermiddel getVervoermiddel() {
        return vervoermiddel;
    }

    public void setVervoermiddel(Vervoermiddel vervoermiddel) {
        this.vervoermiddel = vervoermiddel;
    }

	@Override
	public int hashCode() {
		return Objects.hash(begindatum, einddatum, vervoermiddel);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Onderhoudsbeurt other = (Onderhoudsbeurt) obj;
		return Objects.equals(begindatum, other.begindatum) && Objects.equals(einddatum, other.einddatum)
				&& Objects.equals(vervoermiddel, other.vervoermiddel);
	}
}
