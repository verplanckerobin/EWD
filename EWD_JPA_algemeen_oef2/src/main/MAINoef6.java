package main;

import java.util.List;

import javax.persistence.EntityManager;

import domein.Campus;
import domein.Docent;
import domein.Werkruimte;
import util.JPAUtil;

public class MAINoef6 {

	public static void main(String[] args) {
		// We zoeken alle docenten van Campus Gent die ook in Aalst werken
		// Ze krijgen tijdelijk als werkplaats kelder toegewezen
		
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		entityManager.getTransaction().begin();
		
		Werkruimte wr = entityManager.find(Werkruimte.class, "SCH555");
		
		Campus campusGent = entityManager.createNamedQuery("Campus.findByName", Campus.class).setParameter("naam", "Gent").getSingleResult();
		Campus campusAalst = entityManager.createNamedQuery("Campus.findByName", Campus.class).setParameter("naam", "Aalst").getSingleResult();
		
		if (wr != null && campusGent != null && campusAalst != null) {
			List<Docent> docenten = entityManager.createNamedQuery("Docent.docentenInTweeCampussen", Docent.class)
					.setParameter("campus1", campusGent)
					.setParameter("campus2", campusAalst)
					.getResultList();
			docenten.forEach(d -> d.setWerkruimte(wr));
		}
		
		entityManager.getTransaction().commit();
		entityManager.close();
		JPAUtil.getEntityManagerFactory().close();
	}
}
