package io.pturczyk.rpg.engine.world.event;

import io.pturczyk.rpg.model.EnemyCharacter;
import io.pturczyk.rpg.model.PlayerCharacter;

/**
 * Event triggered in case character encountering an enemy.
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public class EnemyEncounterWorldEvent extends WorldEvent {

	private static final long serialVersionUID = 1L;
	private final EnemyCharacter enemy;

	public EnemyEncounterWorldEvent(PlayerCharacter playerCharacter, EnemyCharacter enemy) {
		super(playerCharacter);
		this.enemy = enemy;
	}

	public EnemyCharacter getEnemy() {
		return enemy;
	}

}
