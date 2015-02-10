package io.pturczyk.rpg.engine.world;

import io.pturczyk.rpg.engine.world.event.generator.WorldEventGenerator;
import io.pturczyk.rpg.model.Coordinates;
import io.pturczyk.rpg.model.PlayerCharacter;
import io.pturczyk.rpg.model.RpgCharacter;
import io.pturczyk.rpg.util.IOUtils;

import java.io.IOException;

/**
 * Builds or retrieves saved {@link GameWorld} 
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public class GameWorldProvider {
	private static final Coordinates WORLD_EDGE_VERTEX = new Coordinates(9, 9);
	private static final Coordinates CHARACTER_START_POINT = new Coordinates(0, 0);
	
	private final IOUtils io;
	private final GameWorldRepository gameWorldRepository;
	private final WorldEventGenerator eventGenerationStrategy;
	private final GameCharacterRoleBuilder gameCharacterRoleBuilder;

	public GameWorldProvider(IOUtils io, GameWorldRepository gameWorldRepository,
			WorldEventGenerator worldEventGenerationStrategy) {
		this.io = io;
		this.gameWorldRepository = gameWorldRepository;
		this.eventGenerationStrategy = worldEventGenerationStrategy;
		this.gameCharacterRoleBuilder = new GameCharacterRoleBuilder();
	}

	/**
	 * Builds (or retrieves saved) {@link GameWorld}
	 * 
	 * @return {@link GameWorld} instance
	 */
	public GameWorld get() {
		GameWorld world = retrieveWorld();

		if (world == null || !io.askYesNo("Restore saved game")) {
			world = createNewWorld();
		}

		return world;
	}

	private PlayerCharacter createNewCharacter() {
		return gameCharacterRoleBuilder.build();
	}

	private GameWorld createNewWorld() {
		return new GameWorld(WORLD_EDGE_VERTEX, createNewCharacter(), CHARACTER_START_POINT, eventGenerationStrategy);
	}

	private GameWorld retrieveWorld() {
		GameWorld world = null;
		try {
			world = gameWorldRepository.retrieve();
		} catch (IOException e) {
			io.say("Error retrieving world.\n");
		}

		return world;
	}

	private class GameCharacterRoleBuilder {
		private static final int DEFAULT_MAX_HEALTH = 100;
		private static final int DEFAULT_ATTACK_EXP = 100;

		/**
		 * Retrieves user input and build the character based on this
		 * 
		 * @return {@link RpgCharacter} from user input
		 */
		public PlayerCharacter build() {
			String name = io.ask("What is the name of your character? ");
			return new PlayerCharacter(name, DEFAULT_MAX_HEALTH, DEFAULT_ATTACK_EXP);
		}
	}
}
