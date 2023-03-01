package domein;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Vervoermiddel implements TebetalenTaks, Serializable {
    
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String nummerplaat;
	
	@OneToMany(mappedBy = "vervoermiddel", cascade = CascadeType.ALL)
    private List<Onderhoudsbeurt> onderhoudsbeurten = new ArrayList<>();

    public Vervoermiddel(String nummerplaat) {
        this.nummerplaat = nummerplaat;
    }
    
    protected Vervoermiddel() {
    	
    }

    public String getNummerplaat() {
        return nummerplaat;
    }

    public void setNummerplaat(String nummerplaat) {
        this.nummerplaat = nummerplaat;
    }
    
    public List<Onderhoudsbeurt> getOnderhoudsbeurten() {
        return Collections.unmodifiableList(onderhoudsbeurten);
    }
    
    public void addOnderhoudsbeurt(Onderhoudsbeurt ob){
        onderhoudsbeurten.add(ob);
    }

    @Override
    public String toString() {
        return String.format("Vervoermiddel{nummerplaat=%s}%n", nummerplaat);
    }

	public long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nummerplaat);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vervoermiddel other = (Vervoermiddel) obj;
		return Objects.equals(nummerplaat, other.nummerplaat);
	}
}
