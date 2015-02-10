package io.pturczyk.rpg.engine.command.handler;

import io.pturczyk.rpg.engine.GameContext;
import io.pturczyk.rpg.engine.command.Command;

/**
 * Abstract {@link Command} handler class. Encapsulates user action logic
 * interacting with the game world.
 * 
 * <p>
 * Override {@link #handle(Command)} when subclassing.
 * <p>
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public abstract class CommandHandler {

	private final String action;
	private final String description;

	public CommandHandler(String action, String description) {
		this.action = action;
		this.description = description;
	}

	/**
	 * Handles the given command
	 * 
	 * @param cmd
	 *            to handle
	 * @param context
	 *            game context
	 */
	public abstract void handle(Command cmd, GameContext context);

	/**
	 * @return action name
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @return action description
	 */
	public String getDescription() {
		return description;
	}
}
