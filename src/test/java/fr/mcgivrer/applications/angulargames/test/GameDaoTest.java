/**
 * 
 */
package fr.mcgivrer.applications.angulargames.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.inject.Inject;

import org.junit.Test;

import fr.mcgivrer.applications.angulargames.dao.GameDao;
import fr.mcgivrer.applications.angulargames.models.Game;
import fr.mcgivrer.applications.angulargames.test.dao.GenericDaoTest;

/**
 * @author FDELORME
 * 
 */
public class GameDaoTest extends GenericDaoTest {

	@Inject
	private GameDao gd = new GameDao(em);

	/**
	 * Test method for
	 * {@link fr.mcgivrer.applications.angulargames.dao.internal.GenericDao#save(java.io.Serializable)}
	 * .
	 */
	@Test
	public void testSave() {
		Game game = new Game("test0", "test", "path/to/image");
		game = gd.save(game);
		assertEquals("Can not create Game object in database.", null,
				game.getId());
	}

	/**
	 * Test method for
	 * {@link fr.mcgivrer.applications.angulargames.dao.internal.GenericDao#findById(java.io.Serializable)}
	 * .
	 */
	@Test
	public void testFindById() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link fr.mcgivrer.applications.angulargames.dao.internal.GenericDao#delete(java.io.Serializable)}
	 * .
	 */
	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link fr.mcgivrer.applications.angulargames.dao.internal.GenericDao#findAll()}
	 * .
	 */
	@Test
	public void testFindAll() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link fr.mcgivrer.applications.angulargames.dao.internal.GenericDao#countAll()}
	 * .
	 */
	@Test
	public void testCountAll() {
		fail("Not yet implemented");
	}

}
