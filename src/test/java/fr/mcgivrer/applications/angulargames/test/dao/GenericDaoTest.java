/**{
 * 
 */
package fr.mcgivrer.applications.angulargames.test.dao;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Before;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import fr.mcgivrer.applications.angulargames.dao.internal.DaoFactory;
import fr.mcgivrer.applications.angulargames.dao.internal.GenericDao;
import fr.mcgivrer.applications.angulargames.models.Game;

/**
 * @author FDELORME
 * 
 */
public class GenericDaoTest extends DatabaseConfiguration {

	protected static Map<String, Game> dataGame = new HashMap<String, Game>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GenericDaoTest() {
		// Entity Detection configuration
		ConfigurationBuilder reflectConfig = new ConfigurationBuilder()
				.addUrls(
						ClasspathHelper
								.forPackage("fr.mcgivrer.applications.dao"))
				.addUrls(ClasspathHelper.forClass(Resource.class))
				.setScanners(new TypeAnnotationsScanner());
		Reflections entitiesFilter = new Reflections(reflectConfig);
		for (Class<?> resource : entitiesFilter
				.getTypesAnnotatedWith(Resource.class)) {
			DaoFactory.add(resource.getSimpleName(),
					(Class<? extends GenericDao>) resource);
		}

	}

	@Before
	public void insertTestdata() {

		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.createNativeQuery("truncate table game").executeUpdate();
			tx.commit();
			tx.begin();
			Game game1 = new Game("Dragon's Dogma", "X360",
					"http://ecx.images-amazon.com/images/I/81bfGYT1I8L._SL80_.jpg");
			em.persist(game1);
			Game game2 = new Game("L.A. Noire", "X360",
					"http://ecx.images-amazon.com/images/I/81NZFcx-%2BBL._SL80_.jpg");
			em.persist(game2);
			Game game3 = new Game("Sonic Generation", "X360",
					"http://ecx.images-amazon.com/images/I/81hlSLQEeoL._SL80_.jpg");
			em.persist(game3);
			Game game4 = new Game("Alan Wake", "X360",
					"http://ecx.images-amazon.com/images/I/41PpoZ62wrL._SL80_.jpg");
			em.persist(game4);

			// Back just created games list
			dataGame.put(game1.getName(), game1);
			dataGame.put(game2.getName(), game2);
			dataGame.put(game3.getName(), game3);
			dataGame.put(game4.getName(), game4);

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		}
	}

	@After
	public void dropTestdata() {

		EntityTransaction tx = em.getTransaction();
		try {

			tx.begin();
			for (Game game : dataGame.values()) {
				em.remove(game);
				game = null;
			}

			tx.commit();
			dataGame.clear();
		} catch (Exception e) {
			tx.rollback();

		}
	}

}
