package main;

import domein.Campus;
import domein.Docent;
import domein.Werkruimte;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import util.JPAUtil;

public class MAINoef3 {
    public static void main(String args[]) {
	EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

	entityManager.getTransaction().begin();

	Docent jan = new Docent(123, "Jan", "Baard", new BigDecimal(8000));
	Docent piet = new Docent(456, "Piet", "Baard", new BigDecimal(10000));
	Docent joris = new Docent(789, "Joris", "ZonderBaard", new BigDecimal(12000));
	Campus gent = new Campus("Gent");
	Campus aalst = new Campus("Aalst");

	Werkruimte zolder = new Werkruimte("SCH123", "zolder", 12, 6);
	Werkruimte kelder = new Werkruimte("SCH555", "kelder", 4, 4);
	Werkruimte dak = new Werkruimte("AA222", "dak", 10, 2);

	entityManager.persist(jan);
	entityManager.persist(piet);
	entityManager.persist(joris);
	entityManager.persist(gent);
	entityManager.persist(aalst);
	entityManager.persist(zolder);
	entityManager.persist(kelder);
	entityManager.persist(dak);

	entityManager.getTransaction().commit();
	entityManager.close();
	JPAUtil.getEntityManagerFactory().close();
    }

}