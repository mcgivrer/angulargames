/**
 * 
 */
package fr.mcgivrer.applications.angulargames.dao.internal;

import java.util.HashMap;
import java.util.Map;

import fr.mcgivrer.applications.angulargames.test.dao.DatabaseConfiguration;

/**
 * @author FDELORME
 * 
 */
public class DaoFactory extends DatabaseConfiguration {
	@SuppressWarnings("rawtypes")
	private static Map<String, Class<? extends GenericDao>> daoRepository = new HashMap<String, Class<? extends GenericDao>>();

	@SuppressWarnings("rawtypes")
	public static Dao get(String name) {
		Class<? extends GenericDao> daoClass = daoRepository.get(name);
		Dao dao = null;
		try {
			dao = daoClass.newInstance();
			dao.setEntityManager(em);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dao;
	}

	@SuppressWarnings("rawtypes")
	public static void add(String name, Class<? extends GenericDao> dao) {
		daoRepository.put(name, dao);
	}

}
