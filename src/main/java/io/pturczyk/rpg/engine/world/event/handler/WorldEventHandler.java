package io.pturczyk.rpg.engine.world.event.handler;

import io.pturczyk.rpg.engine.GameContext;
import io.pturczyk.rpg.engine.world.event.WorldEvent;

import java.lang.reflect.ParameterizedType;

/**
 * Abstract world event handler.
 * 
 * <p>
 * Override {@link #handleEvent(WorldEvent, GameContext)}
 * </p>
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public abstract class WorldEventHandler<T extends WorldEvent> {

	/**
	 * Handles the incoming world event
	 * 
	 * @param event
	 *            to handle
	 * @param context
	 *            game context
	 */
	public abstract void handleEvent(T event, GameContext context);

	/**
	 * @return handled event type
	 */
	public Class<T> getHandledEventType() {
		return (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}
}
