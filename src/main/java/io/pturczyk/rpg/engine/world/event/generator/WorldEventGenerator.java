package io.pturczyk.rpg.engine.world.event.generator;

import io.pturczyk.rpg.engine.world.event.WorldEvent;

/**
 * World event generator interface
 */
public interface WorldEventGenerator {
	/**
	 * Generates a new world event
	 * 
	 * @return produced {@link WorldEvent}
	 */
	WorldEvent generate();
}