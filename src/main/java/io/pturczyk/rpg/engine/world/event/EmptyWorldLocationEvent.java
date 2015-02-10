package io.pturczyk.rpg.engine.world.event;

import io.pturczyk.rpg.model.PlayerCharacter;

/**
 * Event triggered in case of character relocating to an empty terrain
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 *
 */
public class EmptyWorldLocationEvent extends WorldEvent {
	private static final long serialVersionUID = 1L;

	public EmptyWorldLocationEvent(PlayerCharacter playerCharacter) {
		super(playerCharacter);
	}
}
