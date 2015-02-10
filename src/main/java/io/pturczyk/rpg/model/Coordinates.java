package io.pturczyk.rpg.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Coordinates. Immutable class.
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public class Coordinates implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Coordinates NORTH_VECTOR = new Coordinates(0, -1);
	public static final Coordinates SOUTH_VECTOR = new Coordinates(0, 1);
	public static final Coordinates EAST_VECTOR = new Coordinates(1, 0);
	public static final Coordinates WEST_VECTOR = new Coordinates(-1, 0);

	private final int x;
	private final int y;

	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Creates coordinates based on the sum of the current coordinates and the
	 * provided one.
	 * 
	 * @param vector
	 *            to sum
	 * @return new coordinates containing the sum of coordinates
	 */
	public Coordinates add(Coordinates vector) {
		return new Coordinates(this.x + vector.x, this.y + vector.y);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof Coordinates) {
			Coordinates that = (Coordinates) obj;
			return Objects.equals(this.x, that.x) && Objects.deepEquals(this.y, that.y);
		}

		return false;
	}

	@Override
	public String toString() {
		return "Coordinates [x=" + x + ", y=" + y + "]";
	}

}
