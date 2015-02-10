package io.pturczyk.rpg.util;

import java.io.Serializable;
import java.util.Random;

/**
 * Random object generator
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public class RandomGenerator implements Serializable {
	private static final long serialVersionUID = 1L;

	private final Random random = new Random();

	/**
	 * Generates random integer limited from top (inclusive)
	 * 
	 * @param limit
	 *            top limit (not lower than 1)
	 * @return random integer
	 */
	public int nextInt(int limit) {
		if(limit < 1) {
			throw new IllegalArgumentException("limit must be at least 1");
		}
		return random.nextInt(limit + 1);
	}

	/**
	 * Generates a random boolean
	 * 
	 * @return random boolean
	 */
	public boolean nextBoolean() {
		return nextInt(1) == 1;
	}

}
