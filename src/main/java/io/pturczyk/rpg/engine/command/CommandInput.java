package io.pturczyk.rpg.engine.command;


/**
 * Interface used for obtaining commands from User
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public interface CommandInput {
	/**
	 * Retrieves command from user
	 * 
	 * @return retrieved {@link Command}
	 */
	Command get();
}
