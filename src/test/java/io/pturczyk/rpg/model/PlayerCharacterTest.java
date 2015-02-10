package io.pturczyk.rpg.model;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class PlayerCharacterTest {

	@Test
	public void idlePlayerCharacterShouldBecomeBusyWhenTakingDamage() {
		// given
		final int damage = 5;
		final String name = "Pawel";
		final int maxHealth = 100;
		final int attack = 10;
		PlayerCharacter character = new PlayerCharacter(name, maxHealth, attack);
		assertThat(character.isBusy()).isFalse();
		
		// when
		character.takeDamage(damage);
		
		// then
		assertThat(character.isBusy()).isTrue();
		assertThat(character.getHealth()).isEqualTo(maxHealth - damage);
	}
}
