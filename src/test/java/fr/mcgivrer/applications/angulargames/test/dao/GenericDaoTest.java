/**
 * 
 */
package fr.mcgivrer.applications.angulargames.test.dao;

import java.util.Map;
import java.util.Properties;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.ejb.Ejb3Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import fr.mcgivrer.applications.angulargames.models.Game;

/**
 * @author FDELORME
 * 
 */
@SuppressWarnings("deprecation")
public class GenericDaoTest {

	protected static EntityManagerFactory emf;
	protected static EntityManager em;

	@BeforeClass
	public static void init() {
		try {
			if (emf==null) {
				emf = Persistence.createEntityManagerFactory("application");
				if (em==null) {
					em = load(emf);
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private static EntityManager load(EntityManagerFactory emf)
			throws ClassNotFoundException {
		// Entity Detection configuration
		ConfigurationBuilder reflectConfig = new ConfigurationBuilder()
				.addUrls(ClasspathHelper.forPackage("fr.mcgivrer.applications"))
				.addUrls(ClasspathHelper.forClass(Entity.class))
				.setScanners(new ResourcesScanner(),
						new TypeAnnotationsScanner());

		// prepare scan for annotated entities
		Map<String, Object> props = emf.getProperties();

		// Load jdbc driver class onto classpath.
		Class.forName((String) (props.get("hibernate.connection.driver_class"))
				.toString());

		Properties properties = new Properties();
		properties.putAll(props);

		Ejb3Configuration jpaConfig = new Ejb3Configuration();

		// TODO: by default force the auto-commit mode to true. to be
		// removed
		// on next version.
		properties.put("hibernate.connection.autocommit", "true");
		jpaConfig.addProperties(properties);

		// Parse annotated entities and add these to the Hibernate
		// configuration component
		Reflections entitiesFilter = new Reflections(reflectConfig);
		for (Class<?> entity : entitiesFilter
				.getTypesAnnotatedWith(Entity.class)) {
			jpaConfig.addAnnotatedClass(entity);
		}
		return jpaConfig.buildEntityManagerFactory().createEntityManager();
	}

	@Before
	public void insertTestdata() {
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
	}

	@After
	public void dropTestdata() {
		em.createNativeQuery("delete from game").executeUpdate();
	}

}
