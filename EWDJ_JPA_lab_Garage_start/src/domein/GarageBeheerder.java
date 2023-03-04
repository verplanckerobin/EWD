package domein;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GarageBeheerder {

    public final String PERSISTENCE_UNIT_NAME = "EWDJ_JPA_GaragePU";
    private EntityManager em;
    private EntityManagerFactory emf;
    private Map<String, Vervoermiddel> vervoerMap = new HashMap<>();

    public GarageBeheerder() {
        initializePersistentie();
    }

    private void initializePersistentie() {
        openPersistentie();
        GarageData od = new GarageData(this);
        od.populeerData();
    }

    private void openPersistentie() {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = emf.createEntityManager();
    }

    public void closePersistentie() {
        em.close();
        emf.close();
    }

    public List<Auto> geefAutosZonderOnderhoudsbeurtJPA() {
        return em.createNamedQuery("Auto.alleAutosZonderOnderhoud", Auto.class).getResultList();
    }

    public List<Auto> geefAutosMetOnderhoudsbeurtJPA() {
    	return em.createNamedQuery("Auto.alleAutosMetOnderhoud", Auto.class).getResultList();
    }

    public List<Onderhoudsbeurt> geefOnderhoudsbeurtenOpDatumJPA(LocalDate dat) {
    	return em.createNamedQuery("Onderhoudsbeurt.opDatum", Onderhoudsbeurt.class).setParameter("datum", dat).getResultList();
    }

    public void addVervoermiddel(Vervoermiddel v) {
        vervoerMap.put(v.getNummerplaat(), v);
        em.getTransaction().begin();
        em.persist(v);
        em.getTransaction().commit();
    }

    public void addOnderhoudsbeurt(String nrplaat, LocalDate begin, LocalDate einde) {
        Vervoermiddel v = vervoerMap.get(nrplaat);
        Onderhoudsbeurt o = new Onderhoudsbeurt(begin, einde, v);
        v.addOnderhoudsbeurt(o);
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
    }
}
