package fr.mcgivrer.applications.angulargames.services;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.mcgivrer.applications.angulargames.dao.GameDao;
import fr.mcgivrer.applications.angulargames.models.Game;
@Path("/rest/api/game")
public class GameService {
	@Inject
	private GameDao gd;

	/**
	 * Return all games from the database.
	 * 
	 * @return
	 */
	@GET
	@Path("/all")
	@Produces(value = MediaType.APPLICATION_JSON)
	public List<Game> getAll() {
		return gd.findAll();
	}

	/**
	 * Return all games from the database.
	 * 
	 * @return
	 */
	@GET
	@Path("/all/{offset}/{page}")
	@Produces(value = MediaType.APPLICATION_JSON)
	public List<Game> getAll(@PathParam("offset") int offset,
			@PathParam("page") int page) {
		return gd.findAll(offset, page);
	}

	/**
	 * Retrieve one game on its ID.
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("/{gameId}")
	@Produces(value = MediaType.APPLICATION_JSON)
	public Game find(@PathParam(value = "gameId") String id) {
		Game game = null;
		if (id != null && id != "") {
			Long gameIdLong = Long.valueOf(id);
			game = gd.findById(gameIdLong);
		}
		return game;
	}

}
