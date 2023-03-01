package domein;

import javax.persistence.Entity;

@Entity
public class Auto extends Vervoermiddel {

	private static final long serialVersionUID = 1L;

	public Auto(String nummerplaat) {
        super(nummerplaat);
    }
	
	protected Auto() {
		
	}

    @Override
    public double geefVerkeersbelasting() {
        return 77.75;
        //volgens cilinderinhoud
    }
}
