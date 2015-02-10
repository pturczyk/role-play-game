package io.pturczyk.rpg.engine.world.event.generator;

import io.pturczyk.rpg.engine.GameContext;
import io.pturczyk.rpg.engine.world.GameWorld;
import io.pturczyk.rpg.engine.world.event.EmptyWorldLocationEvent;
import io.pturczyk.rpg.engine.world.event.EnemyEncounterWorldEvent;
import io.pturczyk.rpg.engine.world.event.WorldEvent;
import io.pturczyk.rpg.model.Coordinates;
import io.pturczyk.rpg.model.PlayerCharacter;
import io.pturczyk.rpg.util.RandomGenerator;

import java.io.Serializable;

/**
 * Strategy for generating world events. 
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public class RandomWorldEventGenerator implements WorldEventGenerator, Serializable {
	private static final long serialVersionUID = 1L;
	private static final Coordinates START_POINT = new Coordinates(0, 0);

	private final RandomGenerator randomNumberGenerator;
	private final EnemyFactory enemyFactor;
	private final GameContext context;

	public RandomWorldEventGenerator(RandomGenerator randomNumberGenerator, EnemyFactory enemyFactor, GameContext context) {
		this.randomNumberGenerator = randomNumberGenerator;
		this.enemyFactor = enemyFactor;
		this.context = context;
	}

	/**
	 * {@inheritDoc}
	 * <p> Enemy characteristics is based on randomness. </p>
	 */
	@Override
	public WorldEvent generate() {
		GameWorld world = context.getWorld();
		PlayerCharacter playerCharacter = world.getPlayerCharacter();
		
		// the user always has the start point discovered, so generate empty world event
		boolean isStartPoint = world.getPlayerCharacterLocation().equals(START_POINT);
		if ( isStartPoint || randomNumberGenerator.nextBoolean()) {
			return new EmptyWorldLocationEvent(playerCharacter);
		} else {
			return new EnemyEncounterWorldEvent(playerCharacter, enemyFactor.createEnemy());
		}
	}
}
