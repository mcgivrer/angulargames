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
import org.junit.BeforeClass;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

/**
 * A small class to perform JPA persistence.xml file loading and auto detect
 * Entity from a specific package.
 * 
 * @author FDELORME
 * 
 */
@SuppressWarnings("deprecation")
public class DatabaseConfiguration {

	protected static EntityManagerFactory emf;
	protected static EntityManager em;

	/**
	 * Initialize JPA persistence unit.
	 */
	@BeforeClass
	public static void init() {
		try {
			if (emf == null) {
				emf = Persistence.createEntityManagerFactory("application");
				if (em == null) {
					em = loadConfiguration(emf);
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Add annotated entities with the <code>@Entity</code> annotation to the
	 * Ejb3Configuration object, and create <code>EntityManager</code> with the
	 * resulting configuration.
	 * 
	 * @param emf
	 * @return
	 * @throws ClassNotFoundException
	 */
	private static EntityManager loadConfiguration(EntityManagerFactory emf)
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

		/*
		 * TODO: by default force the auto-commit mode to true. to be removed on
		 * next version.
		 */
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
}
