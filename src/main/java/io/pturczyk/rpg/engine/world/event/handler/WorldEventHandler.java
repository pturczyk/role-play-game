package io.pturczyk.rpg.engine.world.event.handler;

import io.pturczyk.rpg.engine.GameContext;
import io.pturczyk.rpg.engine.world.event.WorldEvent;

/**
 * Abstract world event handler.
 * 
 * <p>
 * Override {@link #handleEvent(WorldEvent, GameContext)}
 * </p>
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public abstract class WorldEventHandler {

	private final Class<? extends WorldEvent> eventType;

	public WorldEventHandler(Class<? extends WorldEvent> eventType) {
		this.eventType = eventType;
	}

	/**
	 * Handles the incoming world event
	 * 
	 * @param event
	 *            to handle
	 * @param context
	 *            game context
	 */
	public abstract void handleEvent(WorldEvent event, GameContext context);

	/**
	 * @return handled event type
	 */
	public Class<? extends WorldEvent> getHandledEventType() {
		return eventType;
	}
}
