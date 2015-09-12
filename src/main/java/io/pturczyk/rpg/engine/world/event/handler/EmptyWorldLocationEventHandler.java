package io.pturczyk.rpg.engine.world.event.handler;

import io.pturczyk.rpg.engine.GameContext;
import io.pturczyk.rpg.engine.world.event.EmptyWorldLocationEvent;
import io.pturczyk.rpg.engine.world.event.WorldEvent;
import io.pturczyk.rpg.util.IOUtils;

/**
 * Handler for {@link EmptyWorldLocationEventHandler}. 
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public class EmptyWorldLocationEventHandler extends WorldEventHandler<EmptyWorldLocationEvent> {

	private final IOUtils io;

	public EmptyWorldLocationEventHandler(IOUtils io) {
		this.io = io;
	}
	
	@Override
	public void handleEvent(EmptyWorldLocationEvent event, GameContext context) {
		io.say("Hmm.. nothing interesting here\n");
		context.getWorld().discardPlayerCharacterEnemy();
	}

}
