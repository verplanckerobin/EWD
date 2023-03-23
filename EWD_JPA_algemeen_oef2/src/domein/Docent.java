package domein;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
//Beter hier de named queries definen dan rechtstreeks in de main, dan gebeurt de controle al voor het programma start
@NamedQueries({ @NamedQuery(name = "Docent.findAll", query = "SELECT d FROM Docent d"),
	@NamedQuery(name = "Docent.docentenInTweeCampussen", query = "SELECT d FROM Docent d "
		+ "WHERE :campus1 MEMBER OF d.campussen " + "AND :campus2 MEMBER OF d.campussen") })
@Table(name = "docenten")
public class Docent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // attribuut docentNr wordt gemapped met een veld PERSONEELSNR
    @Column(name = "PERSONEELSNR")
    private int docentNr;

    private String voornaam;
    private String familienaam;
    private BigDecimal wedde;

    // @ManyToMany(fetch=FetchType.EAGER)
    @ManyToMany
    private Set<Campus> campussen = new HashSet<>();

    @ManyToOne
    private Werkruimte werkruimte;

    public Docent(int docentNr, String voornaam, String familienaam, BigDecimal wedde) {
	this.docentNr = docentNr;
	this.voornaam = voornaam;
	this.familienaam = familienaam;
	this.wedde = wedde;
    }

    protected Docent() {
	// nodig voor JPA-ORM tool (default constructor)
    }

    public int getDocentNr() {
	return docentNr;
    }

    public String getVoornaam() {
	return voornaam;
    }

    public String getFamilienaam() {
	return familienaam;
    }

    public BigDecimal getWedde() {
	return wedde;
    }

    public void setWedde(BigDecimal wedde) {
	this.wedde = wedde;
    }

    public void opslag(BigDecimal bedrag) {
	wedde = wedde.add(bedrag);
    }

    public void addCampus(Campus campus) {
	campussen.add(campus);
    }

    public void removeCampus(Campus campus) {
	campussen.remove(campus);
    }

    public void setWerkruimte(Werkruimte w) {
	werkruimte = w;
    }

    public Set<Campus> getCampussen() {
	return Collections.unmodifiableSet(campussen);
    }

    @Override
    public String toString() {
	return String.format("%d %s %s %s %s", docentNr, voornaam, familienaam, wedde, werkruimte);
    }

    @Override
    public int hashCode() {
	return Objects.hash(docentNr);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Docent other = (Docent) obj;
	return docentNr == other.docentNr;
    }
}