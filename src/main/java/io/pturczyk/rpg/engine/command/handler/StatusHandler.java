package io.pturczyk.rpg.engine.command.handler;

import io.pturczyk.rpg.engine.GameContext;
import io.pturczyk.rpg.engine.command.Command;
import io.pturczyk.rpg.model.PlayerCharacter;
import io.pturczyk.rpg.util.IOUtils;

/**
 * Handles the character status command
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public class StatusHandler extends CommandHandler {

	private static final String ACTION = "status";
	private static final String DESCR = "displays current status of the character";
	private final IOUtils io;

	public StatusHandler(IOUtils io) {
		super(ACTION, DESCR);
		this.io = io;
	}

	@Override
	public void handle(Command cmd, GameContext context) {
		PlayerCharacter playerCharacter = context.getWorld().getPlayerCharacter();
		io.say("%s's status:\n", playerCharacter.getName());
		io.say(" Attack experience: %d\n", playerCharacter.getAttack());
		io.say(" Health: %d\n", playerCharacter.getHealth());
		io.say(" State: %s\n", playerCharacter.getState().name());
	}

}
