package io.pturczyk.rpg.engine;

import io.pturczyk.rpg.engine.command.Command;
import io.pturczyk.rpg.engine.command.CommandInput;
import io.pturczyk.rpg.engine.command.handler.AttackHandler;
import io.pturczyk.rpg.engine.command.handler.CommandHandlerManager;
import io.pturczyk.rpg.engine.command.handler.ExitHandler;
import io.pturczyk.rpg.engine.command.handler.ExploreHandler;
import io.pturczyk.rpg.engine.command.handler.FleeHandler;
import io.pturczyk.rpg.engine.command.handler.MapHandler;
import io.pturczyk.rpg.engine.command.handler.StatusHandler;
import io.pturczyk.rpg.engine.world.FileGameWorldRepository;
import io.pturczyk.rpg.engine.world.GameWorldProvider;
import io.pturczyk.rpg.engine.world.event.generator.RandomEnemyFactory;
import io.pturczyk.rpg.engine.world.event.generator.RandomWorldEventGenerator;
import io.pturczyk.rpg.engine.world.event.handler.EmptyWorldLocationEventHandler;
import io.pturczyk.rpg.engine.world.event.handler.EnemyEncounterWorldEventHandler;
import io.pturczyk.rpg.engine.world.event.handler.WorldEventHandlerManager;
import io.pturczyk.rpg.util.IOUtils;
import io.pturczyk.rpg.util.RandomGenerator;

import java.util.Arrays;

/**
 * Builds and injects all elements required by GameEngine.
 * 
 * TODO: Replace this with DI framework injectors when possible
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public class GameEngineProvider {
	
	private static final String GAME_SAVE_PATH = "rpg.save";
	private final IOUtils io = new IOUtils();
	private final RandomGenerator randomNumberGenerator = new RandomGenerator();
	
	/**
	 * Creates a new {@link GameEngine} instance.
	 * 
	 * @return new {@link GameEngine} instance
	 */
	public GameEngine get() {
		GameContext context = getGameContext();
		GameWorldProvider worldBuilder = createGameWorldBuilder(context);
		CommandInput cmdInput = createCmdInput(context);
		CommandHandlerManager cmdHandlerManager = createHandlerManager(context);
		WorldEventHandlerManager eventHandlerManager = createEventHandlerManager(context);
		return new GameEngine(cmdInput, cmdHandlerManager, eventHandlerManager, worldBuilder, context);
	}

	private GameWorldProvider createGameWorldBuilder(GameContext context) {
		return new GameWorldProvider(
				io, 
				new FileGameWorldRepository(GAME_SAVE_PATH), 
				new RandomWorldEventGenerator(randomNumberGenerator, new RandomEnemyFactory(randomNumberGenerator), context)  
			);
	}

	private GameContext getGameContext() {
		GameContext context = new GameContext();
		context.setOngoing(true);
		return context;
	}

	private CommandInput createCmdInput(GameContext context) {
		return () -> {
			return Command.from(
					io.ask("So, what should %s do now? ", 
							context.getWorld().getPlayerCharacter().getName())
				);
		};
	}
	
	private CommandHandlerManager createHandlerManager(GameContext gameContext) {
		// Add new command handlers here
		ExploreHandler exploreHandler = new ExploreHandler(io);
		FleeHandler fleeHandler = new FleeHandler(io, randomNumberGenerator);
		ExitHandler exitHandler = new ExitHandler(io, new FileGameWorldRepository(GAME_SAVE_PATH));
		AttackHandler attackHandler = new AttackHandler(io, randomNumberGenerator);
		StatusHandler statusHander = new StatusHandler(io);
		MapHandler mapHandler = new MapHandler(io);
		
		return new CommandHandlerManager(
				io,
				gameContext,
				Arrays.asList(exploreHandler, exitHandler, fleeHandler, attackHandler, statusHander, mapHandler)
			);
	}
	
	private WorldEventHandlerManager createEventHandlerManager(GameContext gameContext) {
		// Add new world event handlers here
		EmptyWorldLocationEventHandler emptyLocationHandler = new EmptyWorldLocationEventHandler(io);
		EnemyEncounterWorldEventHandler enemyEncounterHandler = new EnemyEncounterWorldEventHandler(io, randomNumberGenerator);
		
		return new WorldEventHandlerManager(
				gameContext, 
				Arrays.asList(emptyLocationHandler, enemyEncounterHandler)
			);
	}
	
}
