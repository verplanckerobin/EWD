package domein;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(
			name = "Auto.alleAutosZonderOnderhoud", 
			query = "SELECT a FROM Auto a"
					+ "WHERE SIZE(a.onderhoudsbeurten) = 0"),
	@NamedQuery(
			name = "Auto.alleAutosMetOnderhoud", 
			query = "SELECT a FROM Auto a"
					+ "WHERE SIZE(a.onderhoudsbeurten) > 0")
})
public class Auto extends Vervoermiddel {

	private static final long serialVersionUID = 1L;
	
	protected Auto() {
		
	}

	public Auto(String nummerplaat) {
        super(nummerplaat);
    }

    @Override
    public double geefVerkeersbelasting() {
        return 77.75;
        //volgens cilinderinhoud
    }
}
