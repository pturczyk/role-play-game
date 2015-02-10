package io.pturczyk.rpg.engine;

import io.pturczyk.rpg.engine.command.Command;
import io.pturczyk.rpg.engine.command.CommandInput;
import io.pturczyk.rpg.engine.command.handler.CommandHandlerManager;
import io.pturczyk.rpg.engine.world.GameWorldProvider;
import io.pturczyk.rpg.engine.world.event.WorldEvent;
import io.pturczyk.rpg.engine.world.event.handler.WorldEventHandlerManager;

/**
 * Main game engine class.
 * 
 * <p>
 * Invoke {@link #start(String[])} for starting game.
 * </p>
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public class GameEngine {

	private final CommandInput cmdInput;
	private final CommandHandlerManager cmdHandlerManager;
	private final WorldEventHandlerManager eventHandlerManager;
	private final GameWorldProvider worldProvider;
	private final GameContext context;

	public GameEngine(CommandInput cmdInput,
			CommandHandlerManager cmdHandlerManager,
			WorldEventHandlerManager eventHandlerManager,
			GameWorldProvider worldProvider, GameContext context) {
		this.cmdInput = cmdInput;
		this.cmdHandlerManager = cmdHandlerManager;
		this.eventHandlerManager = eventHandlerManager;
		this.worldProvider = worldProvider;
		this.context = context;
	}

	/**
	 * Starts the game.
	 * 
	 * <p>
	 * Method will block thread until the game exits.
	 * </p>
	 */
	public void start() {
		initWorld();
		startMainLoop();
	}

	private void initWorld() {
		context.setWorld(worldProvider.get());
		cmdHandlerManager.printHelp();
	}

	private void startMainLoop() {
		while (isGameOngoing()) {
			processCommand(getUserCommand());
			processWorldEvent();
		}
	}

	private boolean isGameOngoing() {
		return context.isOngoing();
	}

	private Command getUserCommand() {
		return cmdInput.get();
	}

	private void processCommand(Command userCommand) {
		cmdHandlerManager.handle(userCommand);
	}

	private void processWorldEvent() {
		if (context.isTriggerEvent()) {
			context.resetTriggerEvent();
			WorldEvent worldEvent = context.getWorld().getEvent();
			eventHandlerManager.handle(worldEvent);
		}
	}

}
