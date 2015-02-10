package io.pturczyk.rpg.model;

import java.util.Objects;

/**
 * Represents player character
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public class PlayerCharacter extends RpgCharacter {
	private static final long serialVersionUID = 1L;
	private State state;

	public PlayerCharacter(String name, int maxHealth, int attack) {
		super(name, maxHealth, attack);
		this.state = State.NORMAL;
	}

	/**
	 * Sets the characters state
	 * 
	 * @param state
	 *            to set
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * @return Tells if user is busy
	 */
	public boolean isBusy() {
		return !isNormal();
	}

	/**
	 * @return Tells if user is in normal state
	 */
	public boolean isNormal() {
		return state == State.NORMAL;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>Impacts character {@link #state}</p>
	 */
	@Override
	public void takeDamage(int damage) {
		setState(State.BUSY);
		super.takeDamage(damage);
	}

	/**
	 * @return current character state
	 */
	public State getState() {
		return state;
	}

	/**
	 * Character state
	 */
	public enum State {
		NORMAL, BUSY,
	}

	@Override
	public String toString() {
		return "PlayerCharacter [state=" + state + super.toString() + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		// lets make sure equals symmetry is honored due to the inheritance problem
		if (obj instanceof PlayerCharacter) {
			PlayerCharacter that = (PlayerCharacter) obj;
			return that.getClass().isInstance(this) && 
				   Objects.equals(this.state, that.state) && 
				   super.equals(that);
		}
		
		return false;
	}
	@Override
	public int hashCode() {
		return Objects.hash(state) + super.hashCode();
	}
}
