package io.pturczyk.rpg.engine.world.event.handler;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;
import io.pturczyk.rpg.engine.GameContext;
import io.pturczyk.rpg.engine.world.event.WorldEvent;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Manager for {@link WorldEvent} handlers
 * 
 * <p>
 * Finds matching handler for requested {@link WorldEvent} and passes command to
 * found handler for processing.
 * </p>
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public class WorldEventHandlerManager {

	private final GameContext context;
	private Map<Class<? extends WorldEvent>, WorldEventHandler> eventHandlerMap;

	public WorldEventHandlerManager(GameContext context, List<WorldEventHandler> handlers) {
		requireNonNull(context);
		requireNonNull(handlers);
		
		this.context = context;

		addHandlers(handlers);
	}

	private void addHandlers(List<WorldEventHandler> handlers) {
		eventHandlerMap = handlers.stream().collect(
				toMap(WorldEventHandler::getHandledEventType, 
					  Function.<WorldEventHandler> identity()) 
				);
	}

	/**
	 * Handles the incoming event.
	 * 
	 * @param worldEvent
	 *            event to handle
	 */
	public void handle(WorldEvent worldEvent) {
		WorldEventHandler eventHandler = eventHandlerMap.get(worldEvent.getClass());
		if (eventHandler != null) {
			eventHandler.handleEvent(worldEvent, context);
		}
	}
}
