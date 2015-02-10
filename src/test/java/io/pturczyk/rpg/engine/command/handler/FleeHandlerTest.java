package io.pturczyk.rpg.engine.command.handler;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import io.pturczyk.rpg.engine.GameContext;
import io.pturczyk.rpg.engine.command.Command;
import io.pturczyk.rpg.model.Coordinates;
import io.pturczyk.rpg.model.EnemyCharacter;
import io.pturczyk.rpg.model.PlayerCharacter;
import io.pturczyk.rpg.util.IOUtils;
import io.pturczyk.rpg.util.RandomGenerator;

import org.junit.Before;
import org.junit.Test;

public class FleeHandlerTest {
	// class under test
	private FleeHandler handler;
	private IOUtils ioMock;
	private RandomGenerator randomUtilsMock;

	@Before
	public void setup() {
		ioMock = mock(IOUtils.class);
		randomUtilsMock = mock(RandomGenerator.class);
		handler = new FleeHandler(ioMock, randomUtilsMock);
	}

	@Test
	public void characterShouldNotFleeIfThereIsNoEnemy() {
		// given
		GameContext contextMock = mock(GameContext.class, RETURNS_DEEP_STUBS);
		PlayerCharacter characterMock = mock(PlayerCharacter.class);
		Coordinates location = new Coordinates(5, 5);
		given(contextMock.getWorld().getPlayerCharacterLocation()).willReturn(location);
		given(contextMock.getWorld().hasPlayerCharacterEnemy()).willReturn(false);
		given(contextMock.getWorld().getPlayerCharacter()).willReturn(characterMock);
		Command cmd = mock(Command.class);

		// when
		handler.handle(cmd, contextMock);

		// then
		verify(contextMock, never()).triggerEvent();
		verify(contextMock.getWorld(), never()).fleePlayerCharacter();
	}
	
	@Test
	public void characterShouldFailToFleeFromEnemy() {
		// given
		GameContext contextMock = mock(GameContext.class, RETURNS_DEEP_STUBS);
		PlayerCharacter characterMock = mock(PlayerCharacter.class);
		Coordinates location = new Coordinates(5, 5);
		given(contextMock.getWorld().getPlayerCharacterLocation()).willReturn(location);
		EnemyCharacter enemy = mock(EnemyCharacter.class);
		given(enemy.isHostile()).willReturn(true);
		given(contextMock.getWorld().hasPlayerCharacterEnemy()).willReturn(true);
		given(contextMock.getWorld().getPlayerCharacterEnemy()).willReturn(enemy);
		given(contextMock.getWorld().getPlayerCharacter()).willReturn(characterMock);
		given(randomUtilsMock.nextInt(anyInt())).willReturn(1);
		Command cmd = mock(Command.class);

		// when
		handler.handle(cmd, contextMock);

		// then
		verify(contextMock).triggerEvent();
		verify(contextMock.getWorld(), never()).fleePlayerCharacter();
		verify(contextMock.getWorld(), never()).discardPlayerCharacterEnemy();
	}
	
	@Test
	public void characterShouldFleeFromOngoingFight() {
		// given
		GameContext contextMock = mock(GameContext.class, RETURNS_DEEP_STUBS);
		PlayerCharacter characterMock = mock(PlayerCharacter.class);
		Coordinates location = new Coordinates(5, 5);
		given(contextMock.getWorld().getPlayerCharacterLocation()).willReturn(location);
		EnemyCharacter enemy = mock(EnemyCharacter.class);
		given(enemy.isHostile()).willReturn(true);
		given(contextMock.getWorld().hasPlayerCharacterEnemy()).willReturn(true);
		given(contextMock.getWorld().getPlayerCharacterEnemy()).willReturn(enemy);
		given(contextMock.getWorld().getPlayerCharacter()).willReturn(characterMock);
		given(randomUtilsMock.nextBoolean()).willReturn(true);
		Command cmd = mock(Command.class);

		// when
		handler.handle(cmd, contextMock);

		// then
		verify(contextMock).triggerEvent();
		verify(contextMock.getWorld()).fleePlayerCharacter();
	}

}

