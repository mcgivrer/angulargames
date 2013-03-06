/**
 * 
 */
package fr.mcgivrer.applications.angulargames.dao;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import fr.mcgivrer.applications.angulargames.dao.internal.GenericDao;
import fr.mcgivrer.applications.angulargames.models.Game;

/**
 * @author FDELORME
 *
 */
@Resource
public class GameDao extends GenericDao<Game, Long> {
	public GameDao(EntityManager em) {
		super(em);
	}

}
