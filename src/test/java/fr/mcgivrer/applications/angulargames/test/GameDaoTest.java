/**
 * 
 */
package fr.mcgivrer.applications.angulargames.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import fr.mcgivrer.applications.angulargames.dao.GameDao;
import fr.mcgivrer.applications.angulargames.models.Game;
import fr.mcgivrer.applications.angulargames.test.dao.GenericDaoTest;

/**
 * @author FDELORME
 * 
 */
public class GameDaoTest extends GenericDaoTest {
	//@Inject
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

		assertNotNull("Can not create Game object in database.", game.getId());
	}

	/**
	 * Test method for
	 * {@link fr.mcgivrer.applications.angulargames.dao.internal.GenericDao#findById(java.io.Serializable)}
	 * .
	 */
	@Test
	public void testFindById() {
		Game game = gd.findById(dataGame.get("Dragon's Dogma").getId());
		assertNotNull("Can not find Game for id = 1", game);
	}

	/**
	 * Test method for
	 * {@link fr.mcgivrer.applications.angulargames.dao.internal.GenericDao#delete(java.io.Serializable)}
	 * .
	 */
	@Test
	public void testDelete() {
		gd.delete(dataGame.get("Dragon's Dogma"));
		Game gameDeleted = gd.findById(dataGame.get("Dragon's Dogma").getId());
		assertEquals("game was not deleted !", null, gameDeleted);
	}

	/**
	 * Test method for
	 * {@link fr.mcgivrer.applications.angulargames.dao.internal.GenericDao#findAll()}
	 * .
	 */
	@Test
	public void testFindAll() {
		List<Game> games = gd.findAll();
		assertEquals("Find all didn't retrieve all games", 4, games.size());
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
