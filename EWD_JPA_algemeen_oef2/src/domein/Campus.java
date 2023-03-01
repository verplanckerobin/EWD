package domein;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
// Beter hier de named queries definen dan rechtstreeks in de main, dan gebeurt de controle al voor het programma start
@NamedQueries({
    @NamedQuery(
           name = "Campus.findAll", 
           query = "SELECT c FROM Campus c"),
    @NamedQuery(
    		name = "Campus.findByName", 
    		query = "SELECT c FROM Campus c WHERE c.campusNaam = :naam")
})
public class Campus implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int campusID;
	   
    private String campusNaam;

    //@ManyToMany(fetch=FetchType.EAGER, mappedBy = "campussen")
    @ManyToMany(mappedBy = "campussen")
    private final  Set<Docent> docenten = new HashSet<>();

    public Campus(String campusNaam) {
        this.campusNaam = campusNaam;
    }
    
    protected Campus() {
    	//nodig voor JPA-ORM tool (default constructor)
    }
    
    public String getCampusNaam() {
        return this.campusNaam;
    }

    public void setCampusNaam(String campusNaam) {
        this.campusNaam = campusNaam;
    }

    public Set<Docent> getDocenten() {
    	//uitleg nog eens bekijken les1_deel2_min14
        return Collections.unmodifiableSet(docenten);
    }
    
    public void addDocent(Docent docent) {
        docenten.add(docent);
    }

    public void removeDocent(Docent docent) {
        docenten.remove(docent);
    }
    
    @Override
    public String toString() {
        return String.format("%d %s", campusID, campusNaam);
    }

	@Override
	public int hashCode() {
		return Objects.hash(campusNaam);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Campus other = (Campus) obj;
		return Objects.equals(campusNaam, other.campusNaam);
	}

    // NOOIT een PK kiezen in equals! 
//	@Override
//	public int hashCode() {
//		return Objects.hash(campusID);
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Campus other = (Campus) obj;
//		return campusID == other.campusID;
//	}
}