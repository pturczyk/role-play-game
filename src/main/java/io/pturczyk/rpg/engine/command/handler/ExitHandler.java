package io.pturczyk.rpg.engine.command.handler;

import io.pturczyk.rpg.engine.GameContext;
import io.pturczyk.rpg.engine.command.Command;
import io.pturczyk.rpg.engine.world.GameWorldRepository;
import io.pturczyk.rpg.util.IOUtils;

import java.io.IOException;

/**
 * Handles the exit event.
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public class ExitHandler extends CommandHandler {

	private static final String ACTION = "exit";
	private static final String DESCR = "exits the game";

	private final IOUtils io;
	private final GameWorldRepository gameWorldRepository;

	public ExitHandler(IOUtils io, GameWorldRepository gameWorldRepository) {
		super(ACTION, DESCR);
		this.io = io;
		this.gameWorldRepository = gameWorldRepository;
	}

	@Override
	public void handle(Command cmd, GameContext context) {
		context.setOngoing(false);

		if (io.askYesNo("Do you want to save the current game progress")) {
			try {
				gameWorldRepository.store(context.getWorld());
				io.say("Game stored successfully!\n");
			} catch (IOException e) {
				io.say("Saving game progress failed.\n");
			} 
		}
	}

}
