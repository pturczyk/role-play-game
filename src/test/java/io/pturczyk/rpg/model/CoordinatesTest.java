package io.pturczyk.rpg.model;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class CoordinatesTest {

	@Test
	public void shouldSumCoordinatesCorrectly() {
		// given
		Coordinates a = new Coordinates(1, 1);
		Coordinates b = new Coordinates(2, 2);
		
		// when
		Coordinates c = a.add(b);
		
		// then
		assertThat(c.getX()).isEqualTo(3);
		assertThat(c.getY()).isEqualTo(3);
	}
}
