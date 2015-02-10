package io.pturczyk.rpg.engine.world.event.generator;

import io.pturczyk.rpg.model.EnemyCharacter;
import io.pturczyk.rpg.model.EnemyCharacter.Hostality;
import io.pturczyk.rpg.util.RandomGenerator;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Random Enemy factory
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public class RandomEnemyFactory implements EnemyFactory, Serializable {
	private static final long serialVersionUID = 1L;

	private static final List<String> enemyNames = Arrays.asList("Ugly huge spider", "Dwarf", "Goblin", "Stephen Hawking");
	private static final int MIN = 1;
	private static final int maxHealth = 100;
	private static final int maxAttack = 50;

	private final RandomGenerator random;
	
	public RandomEnemyFactory(RandomGenerator random) {
		this.random = random;
	}
	
	@Override
	public EnemyCharacter createEnemy() {
		return new EnemyCharacter(getRandomName(), getRandomHealth(), getRandomAttack(), getRandomHostility());
	}

	private int getRandomAttack() {
		return Math.max(MIN, random.nextInt(maxAttack));
	}

	private String getRandomName() {
		return enemyNames.get(random.nextInt(enemyNames.size() - 1));
	}

	private int getRandomHealth() {
		return Math.max(MIN, random.nextInt(maxHealth));
	}

	private Hostality getRandomHostility() {
		return Hostality.values()[random.nextInt(Hostality.values().length - 1)];
	}
}
