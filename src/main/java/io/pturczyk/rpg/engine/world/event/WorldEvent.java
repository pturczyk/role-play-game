package io.pturczyk.rpg.engine.world.event;

import java.io.Serializable;

import io.pturczyk.rpg.model.PlayerCharacter;

/**
 * Base class encapsulating a game world event.
 * 
 * <p>
 * A world event might be any event that is coming from the game's world being
 * indirectly triggered by an user action (in other words it's the worlds
 * 'reaction' to that action)
 * </p>
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public abstract class WorldEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	private final PlayerCharacter playerCharacter;

	public WorldEvent(PlayerCharacter playerCharacter) {
		this.playerCharacter = playerCharacter;
	}

	public PlayerCharacter getPlayerCharacter() {
		return playerCharacter;
	}
}
