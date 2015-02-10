package io.pturczyk.rpg.engine.command.handler;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import io.pturczyk.rpg.engine.GameContext;
import io.pturczyk.rpg.engine.command.Command;
import io.pturczyk.rpg.model.PlayerCharacter;
import io.pturczyk.rpg.model.PlayerCharacter.State;
import io.pturczyk.rpg.util.IOUtils;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class StatusHandlerTest {
	// class under test
	private StatusHandler handler;
	private IOUtils ioMock;

	@Before
	public void setup() {
		ioMock = mock(IOUtils.class);
		handler = new StatusHandler(ioMock);
	}

	@Test
	public void shouldPrintProperCharacterStatus() {
		// given
		final String name = "Pawel";
		final int attack = 10;
		final int health = 100;
		final State state = State.NORMAL;;
		
		PlayerCharacter player = mock(PlayerCharacter.class);
		given(player.getName()).willReturn(name);
		given(player.getAttack()).willReturn(attack);
		given(player.getHealth()).willReturn(health);
		given(player.getState()).willReturn(state);

		GameContext context = mock(GameContext.class, RETURNS_DEEP_STUBS);
		given(context.getWorld().getPlayerCharacter()).willReturn(player);
		Command cmd = mock(Command.class);
		
		// when
		handler.handle(cmd, context);
		
		// then
		ArgumentCaptor<?> statusValueCaptor = ArgumentCaptor.forClass(Object.class);
		verify(ioMock, times(4)).say(anyString(), statusValueCaptor.capture());

		List<?> allValues = statusValueCaptor.getAllValues();
		assertThat(allValues.get(0)).isEqualTo(name);
		assertThat(allValues.get(1)).isEqualTo(attack);
		assertThat(allValues.get(2)).isEqualTo(health);
		assertThat(allValues.get(3)).isEqualTo(state.name());
	}

}
