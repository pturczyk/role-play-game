package io.pturczyk.rpg.engine;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import io.pturczyk.rpg.engine.world.GameWorld;
import io.pturczyk.rpg.model.PlayerCharacter;

import org.junit.Before;
import org.junit.Test;

public class GameContextTest {

	// class under test
	private GameContext context;
	private GameWorld worldMock;

	@Before
	public void setup() {
		worldMock = mock(GameWorld.class);
		context = new GameContext();
		context.setWorld(worldMock);
	}
	
	@Test
	public void shouldSetTriggerEventFlagWhenRequested() {
		// when
		context.triggerEvent();
		
		// then
		assertThat(context.isTriggerEvent()).isTrue();
	}
	
	@Test
	public void shouldResetTriggerEventWhenRequested() {
		// given
		context.triggerEvent();
		
		// when
		context.resetTriggerEvent();
		
		// then
		assertThat(context.isTriggerEvent()).isFalse();
	}
	
	@Test
	public void shouldConsiderGameOngoingWhenFlagIsSetAndCharacterIsAlive() {
		// given
		PlayerCharacter playerCharacter = mock(PlayerCharacter.class);
		given(playerCharacter.isDead()).willReturn(false);
		
		given(worldMock.getPlayerCharacter()).willReturn(playerCharacter);
		context.setOngoing(true);
		
		// when
		boolean gameOngoing = context.isOngoing();
		
		// then
		assertThat(gameOngoing).isTrue();
	}
	
	@Test
	public void shouldConsiderGameNotOngoingWhenCharacterIsDead() {
		// given
		PlayerCharacter playerCharacter = mock(PlayerCharacter.class);
		given(playerCharacter.isDead()).willReturn(true);
		
		given(worldMock.getPlayerCharacter()).willReturn(playerCharacter);
		context.setOngoing(true);
		
		// when
		boolean gameOngoing = context.isOngoing();
		
		// then
		assertThat(gameOngoing).isFalse();
	}
	
}
