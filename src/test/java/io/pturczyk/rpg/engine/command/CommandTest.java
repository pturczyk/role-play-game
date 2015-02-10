package io.pturczyk.rpg.engine.command;

import static org.fest.assertions.Assertions.assertThat;
import io.pturczyk.rpg.engine.command.Command;

import org.junit.Test;

public class CommandTest {
	
	@Test
	public void shouldConstructCommandWithParamsFromUserInput() {
		// given
		final String action = "pick";
		final String param = "sword";
		final String userInput = String.format("%s %s", action, param);

		// when
		Command cmd = Command.from(userInput);
		
		// then
		assertThat(cmd.getAction()).isEqualTo(action);
		assertThat(cmd.getArguments()).containsOnly(param);
	}
	
	@Test
	public void shouldConstructNoopCommandFromEmptyUserString() {
		// given
		final String emptyUserInput = "";
		
		// when
		Command cmd = Command.from(emptyUserInput);
		
		// then
		assertThat(cmd).isSameAs(Command.NOOP_COMMAND);
	}
}
