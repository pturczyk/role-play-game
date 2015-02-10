package io.pturczyk.rpg.engine;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import io.pturczyk.rpg.engine.command.Command;
import io.pturczyk.rpg.engine.command.CommandInput;
import io.pturczyk.rpg.engine.command.handler.CommandHandlerManager;
import io.pturczyk.rpg.engine.world.GameWorld;
import io.pturczyk.rpg.engine.world.GameWorldProvider;
import io.pturczyk.rpg.engine.world.event.WorldEvent;
import io.pturczyk.rpg.engine.world.event.handler.WorldEventHandlerManager;

import org.junit.Before;
import org.junit.Test;

public class GameEngineTest {

	// class under test
	private GameEngine gameEngine;
	private CommandInput commandInputMock;
	private CommandHandlerManager commandHandlerMock;
	private WorldEventHandlerManager eventHandlerMock;
	private GameWorldProvider worldBuilder;
	private GameContext contextMock;

	@Before
	public void setup() {
		commandInputMock = mock(CommandInput.class);
		commandHandlerMock = mock(CommandHandlerManager.class);
		worldBuilder = mock(GameWorldProvider.class);
		contextMock = mock(GameContext.class, RETURNS_DEEP_STUBS);
		eventHandlerMock = mock(WorldEventHandlerManager.class);
		gameEngine = new GameEngine(commandInputMock, commandHandlerMock, eventHandlerMock, worldBuilder, contextMock);
	}


	@Test
	public void shouldInitializedGameContextWithProvidedWorldDuringStart() {
		// given
		GameWorld worldMock = mock(GameWorld.class);
		given(worldBuilder.get()).willReturn(worldMock);

		given(contextMock.isOngoing()).willReturn(false);

		// when
		gameEngine.start();

		// then
		verify(contextMock).setWorld(worldMock);
	}
	
	@Test
	public void shouldProcessUserInputOnGameEngineStart() {
		// given
		Command cmd = Command.from("go south");
		given(commandInputMock.get()).willReturn(cmd);
		given(contextMock.isOngoing()).willReturn(true, false);

		// when
		gameEngine.start();

		// then
		verify(commandHandlerMock).handle(cmd);
	}
	
	@Test
	public void shouldProcessWorldEventWhenTriggerEventIsSet() {
		// given
		GameWorld worldMock = mock(GameWorld.class);
		
		WorldEvent eventMock = mock(WorldEvent.class);
		given(worldMock.getEvent()).willReturn(eventMock);
		
		Command cmd = Command.from("go south");
		given(commandInputMock.get()).willReturn(cmd);
		
		given(contextMock.getWorld()).willReturn(worldMock);
		given(contextMock.isOngoing()).willReturn(true, false);
		given(contextMock.isTriggerEvent()).willReturn(true, false);

		// when
		gameEngine.start();

		// then
		verify(contextMock).resetTriggerEvent();
		verify(worldMock).getEvent();
		verify(eventHandlerMock).handle(eventMock);
	}
	
	@Test
	public void shouldNotProcessWorldEventWhenTriggerEventIsNotSet() {
		// given
		GameWorld worldMock = mock(GameWorld.class);
		
		WorldEvent eventMock = mock(WorldEvent.class);
		given(worldMock.getEvent()).willReturn(eventMock);
		
		Command cmd = Command.from("go south");
		given(commandInputMock.get()).willReturn(cmd);
		
		given(contextMock.getWorld()).willReturn(worldMock);
		given(contextMock.isOngoing()).willReturn(true, false);
		given(contextMock.isTriggerEvent()).willReturn(false, false);

		// when
		gameEngine.start();

		// then
		verify(contextMock, never()).resetTriggerEvent();
		verify(worldMock, never()).getEvent();
		verify(eventHandlerMock, never()).handle(eventMock);
	}
}
