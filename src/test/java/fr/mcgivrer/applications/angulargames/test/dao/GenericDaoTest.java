/**
 * 
 */
package fr.mcgivrer.applications.angulargames.test.dao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import fr.mcgivrer.applications.angulargames.models.Game;

/**
 * @author FDELORME
 * 
 */
public class GenericDaoTest {

	protected static EntityManager em;

	@BeforeClass
	public static void init() {
		try {
			Class.forName("org.hsqldb.jdbcDriver");

			if (em == null) {
				em = (EntityManager) Persistence.createEntityManagerFactory(
						"application").createEntityManager();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Before
	public void insertTestdata() {
		Game game1 = new Game("Dragon's Dogma","X360","http://ecx.images-amazon.com/images/I/81bfGYT1I8L._SL80_.jpg");
		em.persist(game1);
		Game game2 = new Game("L.A. Noire","X360","http://ecx.images-amazon.com/images/I/81NZFcx-%2BBL._SL80_.jpg");
		em.persist(game2);
		Game game3 = new Game("Sonic Generation","X360","http://ecx.images-amazon.com/images/I/81hlSLQEeoL._SL80_.jpg");
		em.persist(game3);
		Game game4 = new Game("Alan Wake","X360","http://ecx.images-amazon.com/images/I/41PpoZ62wrL._SL80_.jpg");
		em.persist(game4);
	}

	@After
	public void dropTestdata() {
		em.createQuery("delete from games").executeUpdate();
	}

}
