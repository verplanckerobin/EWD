package domein;

import java.util.List;

public class GarageController {

    private GarageBeheerder gb = new GarageBeheerder();

    public List<String> geefAutosZonderOnderhoudsbeurt() {
    	List<Auto> li = gb.geefAutosZonderOnderhoudsbeurtJPA();
        return null;
    }

    public List<String> geefAutosMetOnderhoudsbeurt() {
    	List<Auto> li = gb.geefAutosMetOnderhoudsbeurtJPA();
    	return null;
    }

    public List<String> geefOnderhoudsbeurtenOpDatum(int jaar, int maand, int dag) {
        return null;
    }

    public void close() {
        gb.closePersistentie();
    }

}
