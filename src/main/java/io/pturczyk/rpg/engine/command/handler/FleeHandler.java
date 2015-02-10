package io.pturczyk.rpg.engine.command.handler;

import io.pturczyk.rpg.engine.GameContext;
import io.pturczyk.rpg.engine.command.Command;
import io.pturczyk.rpg.engine.world.GameWorld;
import io.pturczyk.rpg.model.PlayerCharacter;
import io.pturczyk.rpg.model.PlayerCharacter.State;
import io.pturczyk.rpg.util.IOUtils;
import io.pturczyk.rpg.util.RandomGenerator;

/**
 * Handles the escape from an attack logic.
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public class FleeHandler extends CommandHandler {

	private static final String ACTION = "flee";
	private static final String DESCR = "flees from an ongoing fight";

	private final IOUtils io;
	private final RandomGenerator randomGenerator;

	public FleeHandler(IOUtils io, RandomGenerator randomGenerator) {
		super(ACTION, DESCR);
		this.io = io;
		this.randomGenerator = randomGenerator;
	}

	@Override
	public void handle(Command cmd, GameContext context) {
		GameWorld world = context.getWorld();
		PlayerCharacter playerCharacter = world.getPlayerCharacter();
		
		if (isNoReasonToFlee(world)) {
			handleNoReasonToFlee(playerCharacter);
		} else if (randomGenerator.nextBoolean()) {
			handleFleeSuccess(context, world, playerCharacter);
		} else {
			handleFleeFailure(context, world, playerCharacter);
		}
	}

	private boolean isNoReasonToFlee(GameWorld world) {
		return !world.hasPlayerCharacterEnemy() || 
				world.getPlayerCharacterEnemy().isDead() ||
			   !world.getPlayerCharacterEnemy().isHostile();
	}

	private void handleNoReasonToFlee(PlayerCharacter playerCharacter) {
		io.say("%s is scratching his head. There is nothing to run from.\n", playerCharacter.getName());
	}

	private void handleFleeSuccess(GameContext context, GameWorld world, PlayerCharacter playerCharacter) {
		io.say("%s is escaping %s to previous safe location.\n", playerCharacter.getName(), 
				world.getPlayerCharacterEnemy().getName());
		playerCharacter.setState(State.NORMAL);
		world.fleePlayerCharacter();
		context.triggerEvent();
	}

	private void handleFleeFailure(GameContext context, GameWorld world, PlayerCharacter playerCharacter) {
		io.say("%s is not able to escape from %s. Let him fight like a man.\n", playerCharacter.getName(), 
				world.getPlayerCharacterEnemy().getName());
		context.triggerEvent();
	}

}
