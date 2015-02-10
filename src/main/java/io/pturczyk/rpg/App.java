package io.pturczyk.rpg;

import io.pturczyk.rpg.engine.GameEngine;
import io.pturczyk.rpg.engine.GameEngineProvider;

/**
 * Application entry point
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public class App {
	public static void main(String[] args) {
		GameEngineProvider engineProvider = new GameEngineProvider();
		GameEngine engine = engineProvider.get();
		engine.start();
	}
}
