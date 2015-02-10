package io.pturczyk.rpg.engine.command.handler;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import io.pturczyk.rpg.engine.GameContext;
import io.pturczyk.rpg.engine.command.Command;
import io.pturczyk.rpg.engine.world.GameWorld;
import io.pturczyk.rpg.engine.world.event.EmptyWorldLocationEvent;
import io.pturczyk.rpg.engine.world.event.EnemyEncounterWorldEvent;
import io.pturczyk.rpg.model.Coordinates;
import io.pturczyk.rpg.model.EnemyCharacter;
import io.pturczyk.rpg.model.PlayerCharacter;
import io.pturczyk.rpg.util.IOUtils;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class MapHandlerTest {
	// class under test
	private MapHandler handler;
	private IOUtils ioMock;

	@Before
	public void setup() {
		ioMock = mock(IOUtils.class);
		handler = new MapHandler(ioMock);
	}

	@Test
	public void shouldPrintProperCharacterStatus() {
		// given
		Command cmd = mock(Command.class);

		GameContext context = mock(GameContext.class, RETURNS_DEEP_STUBS);

		EmptyWorldLocationEvent emptyEvent = new EmptyWorldLocationEvent(mock(PlayerCharacter.class));
		EnemyEncounterWorldEvent enemyEvent = new EnemyEncounterWorldEvent(mock(PlayerCharacter.class), mock(EnemyCharacter.class));

		GameWorld worldMock = mock(GameWorld.class);

		PlayerCharacter player = mock(PlayerCharacter.class);
		given(worldMock.getPlayerCharacterLocation()).willReturn(new Coordinates(0, 1));

		given(context.getWorld()).willReturn(worldMock);
		given(worldMock.getPlayerCharacter()).willReturn(player);
		given(worldMock.getEdgeVertex()).willReturn(new Coordinates(1, 1));
		given(worldMock.peek(new Coordinates(0, 0))).willReturn(emptyEvent);
		given(worldMock.peek(new Coordinates(1, 0))).willReturn(enemyEvent);
		given(worldMock.peek(new Coordinates(1, 1))).willReturn(null);
		
		// when
		handler.handle(cmd, context);
		
		// then
		ArgumentCaptor<Character> mapCharacterCaptor = ArgumentCaptor.forClass(Character.class);
		verify(ioMock, atLeast(4)).say(anyString(), mapCharacterCaptor.capture());

		// Expected map:
		//   |_||E|
		//   |@||#|
		
		List<Character> allCharacters = mapCharacterCaptor.getAllValues();
		assertThat(allCharacters.get(0)).isEqualTo('_');
		assertThat(allCharacters.get(1)).isEqualTo('E');
		assertThat(allCharacters.get(2)).isEqualTo('@');
		assertThat(allCharacters.get(3)).isEqualTo('#');
	}

}
