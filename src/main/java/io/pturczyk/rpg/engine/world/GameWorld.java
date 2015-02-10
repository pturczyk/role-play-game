package io.pturczyk.rpg.engine.world;

import io.pturczyk.rpg.engine.world.event.WorldEvent;
import io.pturczyk.rpg.engine.world.event.generator.WorldEventGenerator;
import io.pturczyk.rpg.model.Coordinates;
import io.pturczyk.rpg.model.EnemyCharacter;
import io.pturczyk.rpg.model.PlayerCharacter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the game's world (board) with all the characters in it.
 * 
 * <p>
 * Call {@link #getEvent()} to retrieve the current character related event. The
 * event is generated accordingly to the provided {@link WorldEventGenerator}.
 * </p>
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public class GameWorld implements Serializable {
	private static final long serialVersionUID = 1L;

	/** The world does not exist beyond this point */
	private final Coordinates edgeVertex;

	/** Played character */
	private PlayerCharacter playerCharacter;

	/** Current character location */
	private Coordinates playerCharacterLocation;

	/** Previous character location */
	private Coordinates previousPlayerCharacterLocation;

	/** Current enemy */
	private EnemyCharacter playerCharacterEnemy;

	/** Generates world events */
	private final WorldEventGenerator eventGeneratorStrategy;

	/** Event map cache */
	private Map<Coordinates, WorldEvent> eventMap;

	/**
	 * Constructor
	 * 
	 * @param edgeVertex
	 *            (western- and southmost) edge vertex coordinates
	 * @param playerCharacter
	 * 			  player character
	 * @param eventGeneratorStrategy
	 *            world event generator
	 * @param characterLocation
	 *            initial character location
	 */
	public GameWorld(Coordinates edgeVertex, PlayerCharacter playerCharacter, Coordinates characterLocation,
			WorldEventGenerator eventGeneratorStrategy) {
		this.edgeVertex = edgeVertex;
		this.playerCharacter = playerCharacter;
		this.playerCharacterLocation = characterLocation;
		this.previousPlayerCharacterLocation = characterLocation;
		this.eventGeneratorStrategy = eventGeneratorStrategy;
		this.eventMap = new HashMap<>();
	}

	/**
	 * @return current world event
	 */
	public WorldEvent getEvent() {
		WorldEvent worldEvent = peek(getPlayerCharacterLocation());
		if (worldEvent == null) {
			worldEvent = eventGeneratorStrategy.generate();
			offer(worldEvent, getPlayerCharacterLocation());
		}
		return worldEvent;
	}
	
	/**
	 * @return world edge vertex
	 */
	public Coordinates getEdgeVertex() {
		return edgeVertex;
	}

	/**
	 * @return currently played character
	 */
	public PlayerCharacter getPlayerCharacter() {
		return playerCharacter;
	}
	
	/**
	 * Current character enemy
	 * 
	 * @return character enemy if set, null if no enemy
	 */
	public EnemyCharacter getPlayerCharacterEnemy() {
		return playerCharacterEnemy;
	}
	
	/**
	 * Sets the current character enemy
	 * 
	 * @param enemy to set
	 */
	public void setPlayerCharacterEnemy(EnemyCharacter playerCharacterEnemy) {
		this.playerCharacterEnemy = playerCharacterEnemy;
	}

	/**
	 * Discards the current enemy
	 */
	public void discardPlayerCharacterEnemy() {
		setPlayerCharacterEnemy(null);
	}

	/**
	 * @return true if charcater has enemy, false otherwise 
	 */
	public boolean hasPlayerCharacterEnemy() {
		return this.playerCharacterEnemy != null;
	}

	/**
	 * Escapes character to previous location discarding the
	 * current characters enemy at the samer time.
	 */
	public void fleePlayerCharacter() {
		this.playerCharacterLocation = this.previousPlayerCharacterLocation;
		this.discardPlayerCharacterEnemy();
	}

	/**
	 * Peeks event at the given coordinates
	 * 
	 * @param coordinates
	 *            to pick event at
	 * @return peeked event if found or null
	 */
	public WorldEvent peek(Coordinates coordinates) {
		return eventMap.get(coordinates);
	}

	private void offer(WorldEvent worldEvent, Coordinates location) {
		eventMap.put(location, worldEvent);
	}

	/**
	 * Moves character in the given direction
	 * 
	 * @param vector
	 *            movement
	 * @return true if successfully moved, false otherwise
	 */
	public boolean movePlayerCharacter(Coordinates vector) {
		Coordinates newLocation = playerCharacterLocation.add(vector);
		if (isInsideWorld(newLocation)) {
			setPlayerCharacterLocation(newLocation);
			return true;
		}
		return false;
	}
	
	/**
	 * @return current character location
	 */
	public Coordinates getPlayerCharacterLocation() {
		return playerCharacterLocation;
	}

	private void setPlayerCharacterLocation(Coordinates characterLocation) {
		this.previousPlayerCharacterLocation = this.playerCharacterLocation;
		this.playerCharacterLocation = characterLocation;
	}
	
	private boolean isInsideWorld(Coordinates location) {
		return location.getX() <= edgeVertex.getX() && location.getX() >= 0 
				&& location.getY() <= edgeVertex.getY() && location.getY() >= 0;
	}
}
