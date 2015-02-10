package io.pturczyk.rpg.engine.world.event.handler;

import io.pturczyk.rpg.engine.GameContext;
import io.pturczyk.rpg.engine.world.event.EnemyEncounterWorldEvent;
import io.pturczyk.rpg.engine.world.event.WorldEvent;
import io.pturczyk.rpg.model.EnemyCharacter;
import io.pturczyk.rpg.model.PlayerCharacter;
import io.pturczyk.rpg.util.IOUtils;
import io.pturczyk.rpg.util.RandomGenerator;

/**
 * Handler for {@link EnemyEncounterWorldEvent}.
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public class EnemyEncounterWorldEventHandler extends WorldEventHandler {

	private final IOUtils io;
	private final RandomGenerator randomNumberGenerator;

	public EnemyEncounterWorldEventHandler(IOUtils io, RandomGenerator randomNumberGenerator) {
		super(EnemyEncounterWorldEvent.class);
		this.io = io;
		this.randomNumberGenerator = randomNumberGenerator;
	}

	@Override
	public void handleEvent(WorldEvent event, GameContext context) {
		if (event instanceof EnemyEncounterWorldEvent) {
			EnemyEncounterWorldEvent enemyEvent = (EnemyEncounterWorldEvent) event;
			PlayerCharacter playerCharacter = enemyEvent.getPlayerCharacter();
			EnemyCharacter enemy = enemyEvent.getEnemy();
			
			context.getWorld().setPlayerCharacterEnemy(enemy);
			
			if (enemy.isDead()) {
				io.say("%s found a dead %s.\n", playerCharacter.getName(), enemy.getName());
			} else {
				switch (enemy.getHostality()) {
					case LOW :
						handleLowlyHostileEnemy(enemy, playerCharacter, context);
						break;
					case HIGH :
					default :
						handleHighlyHostileEnemy(enemy, playerCharacter, context);
						break;
				}
			}
		}
	}

	private void handleHighlyHostileEnemy(EnemyCharacter enemy, PlayerCharacter playerCharacter, GameContext context) {
		int damage = randomNumberGenerator.nextInt(enemy.getAttack());
		playerCharacter.takeDamage(damage);
		
		if(playerCharacter.isDead()) {
			io.say("%s was killed by a %s.\nGame over.\n", playerCharacter.getName(), enemy.getName());
		} else if(damage == 0){
			io.say("%s dodged an attacked from a %s.\n", playerCharacter.getName(), enemy.getName());
		} else {
			io.say("%s was attacked by a %s. Lost %d health.\n", playerCharacter.getName(), enemy.getName(), damage);
		}
	}

	private void handleLowlyHostileEnemy(EnemyCharacter enemy, PlayerCharacter playerCharacter, GameContext context) {
		io.say("%s encounters a friendly %s.\n", playerCharacter.getName(), enemy.getName());
	}

}
