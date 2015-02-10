package io.pturczyk.rpg.engine.world.event.handler;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import io.pturczyk.rpg.engine.GameContext;
import io.pturczyk.rpg.engine.world.event.EnemyEncounterWorldEvent;
import io.pturczyk.rpg.model.EnemyCharacter;
import io.pturczyk.rpg.model.EnemyCharacter.Hostality;
import io.pturczyk.rpg.model.PlayerCharacter;
import io.pturczyk.rpg.util.IOUtils;
import io.pturczyk.rpg.util.RandomGenerator;

import org.junit.Before;
import org.junit.Test;

public class EnemyEncounterWorldEventHandlerTest {
	// class under test
	private EnemyEncounterWorldEventHandler handler;
	private IOUtils ioMock;
	private RandomGenerator randomUtils;
	
	@Before
	public void setup() {
		ioMock = mock(IOUtils.class);
		randomUtils = mock(RandomGenerator.class);
		handler = new EnemyEncounterWorldEventHandler(ioMock, randomUtils);
	}
	
	@Test
	public void shouldNotTriggerAnyActionOnEncouteringDeadEnemy() {
		// given
		PlayerCharacter player = mock(PlayerCharacter.class, RETURNS_DEEP_STUBS);
		
		EnemyCharacter enemy = mock(EnemyCharacter.class, RETURNS_DEEP_STUBS);
		given(enemy.isDead()).willReturn(true);
		
		GameContext context = mock(GameContext.class, RETURNS_DEEP_STUBS);
		EnemyEncounterWorldEvent event = new EnemyEncounterWorldEvent(player, enemy);
		
		// when
		handler.handleEvent(event, context);
		
		// then
		verify(ioMock).say(anyString(), anyString(), anyString());
		verify(player, never()).takeDamage(anyInt());
	}
	
	@Test
	public void shouldTreatEnemyCharacterWithAnyHostalityAsPotentialEnemy() {
		// given
		PlayerCharacter player = mock(PlayerCharacter.class, RETURNS_DEEP_STUBS);
		
		EnemyCharacter enemy = mock(EnemyCharacter.class, RETURNS_DEEP_STUBS);
		given(enemy.getHostality()).willReturn(Hostality.LOW);
		
		GameContext context = mock(GameContext.class, RETURNS_DEEP_STUBS);
		EnemyEncounterWorldEvent event = new EnemyEncounterWorldEvent(player, enemy);
		
		// when
		handler.handleEvent(event, context);
		
		// then
		verify(context.getWorld()).setPlayerCharacterEnemy(enemy);
	}
	
	@Test
	public void shouldTakeDamageWhenEncounteringHighlyHostileEnemy() {
		// given
		final int expectedDamage = 5;
		given(randomUtils.nextInt(anyInt())).willReturn(expectedDamage);

		PlayerCharacter player = mock(PlayerCharacter.class, RETURNS_DEEP_STUBS);
		
		EnemyCharacter enemy = mock(EnemyCharacter.class, RETURNS_DEEP_STUBS);
		given(enemy.getHostality()).willReturn(Hostality.HIGH);
		
		GameContext context = mock(GameContext.class, RETURNS_DEEP_STUBS);
		EnemyEncounterWorldEvent event = new EnemyEncounterWorldEvent(player, enemy);
		
		
		// when
		handler.handleEvent(event, context);
		
		// then
		verify(player).takeDamage(expectedDamage);
	}
	
}
