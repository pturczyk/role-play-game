package io.pturczyk.rpg.engine.world;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import io.pturczyk.rpg.engine.world.event.generator.WorldEventGenerator;
import io.pturczyk.rpg.util.IOUtils;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class GameWorldProviderTest {
	// class under test
	private GameWorldProvider gameWorldProvider;
	private WorldEventGenerator eventGenerationMock;
	private GameWorldRepository gameWorldRepository;
	private IOUtils ioMock;
	
	@Before
	public void setup() {
		ioMock = mock(IOUtils.class);
		eventGenerationMock = mock(WorldEventGenerator.class);
		gameWorldRepository = mock(FileGameWorldRepository.class);
		gameWorldProvider = new GameWorldProvider(ioMock, gameWorldRepository, eventGenerationMock);
	}
	
	@Test
	public void shouldCreateNewWorldWhenNoSavedGamesFound() throws IOException {
		// when
		GameWorld world = gameWorldProvider.get();
		
		// then
		assertThat(world).isNotNull();
		verify(ioMock, never()).askYesNo(anyString());
	}
	
	@Test
	public void shouldNotRestoreFoundSavedGameUponUserDecision() throws IOException {
		// given
		GameWorld storedWorldMock = mock(GameWorld.class);
		given(gameWorldRepository.retrieve()).willReturn(storedWorldMock);
		given(ioMock.askYesNo(anyString(), any())).willReturn(false);
		
		// when
		GameWorld world = gameWorldProvider.get();
		
		// then
		assertThat(world).isNotNull();
		assertThat(world).isNotSameAs(storedWorldMock);
	}
	
	@Test
	public void shouldRestoreFoundSavedGameUponUserDecision() throws IOException {
		// given
		GameWorld storedWorldMock = mock(GameWorld.class);
		given(gameWorldRepository.retrieve()).willReturn(storedWorldMock);
		given(ioMock.askYesNo(anyString())).willReturn(true);
		
		// when
		GameWorld world = gameWorldProvider.get();
		
		// then
		assertThat(world).isNotNull();
		assertThat(world).isSameAs(storedWorldMock);
	}
	
}
