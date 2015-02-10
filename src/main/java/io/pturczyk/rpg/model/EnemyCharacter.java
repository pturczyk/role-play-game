package io.pturczyk.rpg.model;

import java.util.Objects;

/**
 * Represents an enemy character.
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public class EnemyCharacter extends RpgCharacter {
	private static final long serialVersionUID = 1L;

	private Hostality hostality;

	public EnemyCharacter(String name, int maxHealth, int attack, Hostality hostality) {
		super(name, maxHealth, attack);
		this.hostality = hostality;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>May impact character hostality</p>
	 */
	@Override
	public void takeDamage(int damage) {
		// if creature wasn't hostile so far, after this attack it certainly will
		if (getHostality() == Hostality.LOW) {
			this.hostality = Hostality.HIGH;
		}
		super.takeDamage(damage);
	}

	/**
	 * @return current hostality
	 */
	public Hostality getHostality() {
		return hostality;
	}
	
	/** 
	 * @return true if enemy is hostile, false otherwise
	 */
	public boolean isHostile() {
		return hostality == Hostality.HIGH;
	}

	/**
	 * Describes enemy hostality 
	 */
	public enum Hostality {
		LOW, HIGH
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		// lets make sure equals symmetry is honored due to the inheritance problem
		if (obj instanceof EnemyCharacter) {
			EnemyCharacter that = (EnemyCharacter) obj;
			return that.getClass().isInstance(this) && 
				   Objects.equals(this.hostality, that.hostality) && 
				   super.equals(that);
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(hostality) + super.hashCode();
	}
}
