package io.pturczyk.rpg.engine.world.event.generator;

import io.pturczyk.rpg.model.EnemyCharacter;

/**
 * Factory for {@link EnemyCharacter}'s
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public interface EnemyFactory {

	/**
	 * Creates an enemy instance
	 */
	public abstract EnemyCharacter createEnemy();

}