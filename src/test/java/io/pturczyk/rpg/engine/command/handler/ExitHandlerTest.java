package io.pturczyk.rpg.engine.command.handler;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import io.pturczyk.rpg.engine.GameContext;
import io.pturczyk.rpg.engine.command.Command;
import io.pturczyk.rpg.engine.world.GameWorld;
import io.pturczyk.rpg.engine.world.FileGameWorldRepository;
import io.pturczyk.rpg.util.IOUtils;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class ExitHandlerTest {
	// class under test
	private ExitHandler handler;
	private IOUtils ioMock;
	private FileGameWorldRepository gameWorldRepositoryMock;

	@Before
	public void setup() {
		gameWorldRepositoryMock = mock(FileGameWorldRepository.class);
		ioMock = mock(IOUtils.class);
		handler = new ExitHandler(ioMock, gameWorldRepositoryMock);
	}

	@Test
	public void shouldSetGameStateOngoingToFalseOnExitHandle() {
		// given
		GameContext contextMock = mock(GameContext.class);
		Command cmd = mock(Command.class);

		// when
		handler.handle(cmd, contextMock);

		// then
		verify(contextMock).setOngoing(false);
	}

	@Test
	public void shouldExitGameWithoutSavingGameUponUserDecision() throws IOException {
		// given
		GameContext contextMock = mock(GameContext.class);
		Command cmd = mock(Command.class);
		given(ioMock.askYesNo(anyString())).willReturn(false);

		// when
		handler.handle(cmd, contextMock);

		// then
		verify(contextMock).setOngoing(false);
		verify(ioMock).askYesNo(anyString());
		verify(gameWorldRepositoryMock, never()).store(any(GameWorld.class));
	}
	
	@Test
	public void shouldExitGameSavingGameUponUserDecision() throws IOException {
		// given
		GameContext contextMock = mock(GameContext.class);
		Command cmd = mock(Command.class);
		given(ioMock.askYesNo(anyString())).willReturn(true);

		// when
		handler.handle(cmd, contextMock);

		// then
		verify(contextMock).setOngoing(false);
		verify(ioMock).askYesNo(anyString());
		verify(gameWorldRepositoryMock).store(any(GameWorld.class));
	}
}
