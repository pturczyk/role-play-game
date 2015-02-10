package io.pturczyk.rpg.engine.command;

import static java.util.Arrays.copyOfRange;
import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Objects;

/**
 * Encapsulates user command.
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public class Command {

	public static final Command NOOP_COMMAND = new Command("", new String[] {});

	private final String action;
	private final String[] arguments;

	/**
	 * Constructor
	 * 
	 * @param action
	 *            name
	 * @param arguments
	 *            action arguments
	 */
	public Command(String action, String[] arguments) {
		super();
		this.action = action;
		this.arguments = arguments;
	}

	/**
	 * @return action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @return array of command arguments
	 */
	public String[] getArguments() {
		return arguments;
	}

	/**
	 * @return size of argument array
	 */
	public int getArgumentCount() {
		return arguments.length;
	}

	/**
	 * Creates command from the given input.
	 * 
	 * <p>
	 * If empty input given {@link Command#NOOP_COMMAND} will be returned
	 * </p>
	 * 
	 * @param input
	 *            to transform into command
	 * @return parsed {@link Command}
	 */
	public static Command from(String input) {
		requireNonNull(input);

		String[] inputArgs = input.trim().split("\\s+");
		String action = inputArgs[0];

		if (action.isEmpty()) {
			return NOOP_COMMAND;
		} else {
			String[] params = inputArgs.length == 1 ? new String[] {} : copyOfRange(inputArgs, 1, inputArgs.length);
			return new Command(action, params);
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(action, arguments);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof Command) {
			Command that = (Command) obj;
			return Objects.equals(this.action, that.action)
					&& Objects.deepEquals(this.arguments, that.arguments);
		}

		return false;
	}

	@Override
	public String toString() {
		return "Command [action=" + action + ", arguments="	+ Arrays.toString(arguments) + "]";
	}

}
