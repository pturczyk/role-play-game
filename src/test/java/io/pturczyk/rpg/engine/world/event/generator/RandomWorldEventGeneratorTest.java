package io.pturczyk.rpg.engine.world.event.generator;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import io.pturczyk.rpg.engine.GameContext;
import io.pturczyk.rpg.engine.world.event.EmptyWorldLocationEvent;
import io.pturczyk.rpg.engine.world.event.EnemyEncounterWorldEvent;
import io.pturczyk.rpg.engine.world.event.WorldEvent;
import io.pturczyk.rpg.util.RandomGenerator;

import org.junit.Before;
import org.junit.Test;

public class RandomWorldEventGeneratorTest {
	// class under test
	private RandomWorldEventGenerator generator;
	private RandomGenerator randomUtilsMock;
	private EnemyFactory enemyFactoryMock;
	private GameContext contextMock;

	@Before
	public void setup() {
		randomUtilsMock = mock(RandomGenerator.class);
		enemyFactoryMock = mock(EnemyFactory.class);
		contextMock = mock(GameContext.class, RETURNS_DEEP_STUBS);
		generator = new RandomWorldEventGenerator(randomUtilsMock, enemyFactoryMock, contextMock);
	}

	@Test
	public void shouldGenerateEmptyWorldEventForGivenRandomNumber() {
		// given
		given(randomUtilsMock.nextBoolean()).willReturn(true);
		
		// when
		WorldEvent event = generator.generate();
		
		// then
		assertThat(event).isInstanceOf(EmptyWorldLocationEvent.class);
	}
	
	@Test
	public void shouldGenerateEnemyEncounterWorldEventForGivenRandomNumber() {
		// given
		given(randomUtilsMock.nextBoolean()).willReturn(false);
		
		// when
		WorldEvent event = generator.generate();
		
		// then
		assertThat(event).isInstanceOf(EnemyEncounterWorldEvent.class);
	}

}
