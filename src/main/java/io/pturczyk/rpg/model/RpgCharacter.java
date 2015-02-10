package io.pturczyk.rpg.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Base class for game characters.
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public abstract class RpgCharacter implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** Name of the character */ 
	private String name;
	
	/** Maximal health value */
	private int maxHealth;
	
	/** Current health value */
	private int health;
	
	/** Attack experience/power */
	private int attack;

	public RpgCharacter(String name, int maxHealth, int attack) {
		this.name = name;
		this.maxHealth = maxHealth;
		this.health = maxHealth;
		this.attack = attack;
	}

	/**
	 * @return characters name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return maximal possible health value
	 */
	public int getMaxHealth() {
		return maxHealth;
	}

	/**
	 * @return current health value
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Takes damage from the enemy
	 * 
	 * @param damage
	 *            damage to take
	 * @return taken damage
	 */
	public void takeDamage(int damage) {
		decrHealth(damage);
	}

	/**
	 * Decreases the health at most by the given number. The health cannot be
	 * lower than 0.
	 * 
	 * @param health
	 *            to increase
	 */
	public void decrHealth(int damage) {
		this.health = Math.max(0, this.health - damage);
	}

	/**
	 * Increases the health at most by the given number. The health is capped by
	 * the {@link #maxHealth} value
	 * 
	 * @param health
	 *            to increase
	 */
	public void incrHealth(int health) {
		this.health = Math.min(maxHealth, this.health + health);
	}

	/**
	 * @return current attack experience
	 */
	public int getAttack() {
		return attack;
	}

	/**
	 * Sets the attack experience
	 * 
	 * @param attack
	 *            experience to set
	 */
	public void setAttack(int attack) {
		this.attack = attack;
	}

	/**
	 * @return true if character is dead, false otherwise
	 */
	public boolean isDead() {
		return this.health == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.name, this.maxHealth, this.health, this.attack);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof RpgCharacter) {
			RpgCharacter that = (RpgCharacter) obj;
			return Objects.equals(this.name, that.name) && 
					Objects.equals(this.maxHealth, that.maxHealth) &&
					Objects.equals(this.health, that.health) &&
					Objects.equals(this.attack, that.attack);
		}

		return false;
	}


	@Override
	public String toString() {
		return "RpgCharacter [name=" + name + ", maxHealth=" + maxHealth + ", health=" + health + ", attack=" + attack
				+ "]";
	}
	
}
