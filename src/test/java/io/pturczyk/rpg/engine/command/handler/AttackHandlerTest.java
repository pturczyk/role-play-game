package io.pturczyk.rpg.engine.command.handler;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import io.pturczyk.rpg.engine.GameContext;
import io.pturczyk.rpg.engine.command.Command;
import io.pturczyk.rpg.model.EnemyCharacter;
import io.pturczyk.rpg.util.IOUtils;
import io.pturczyk.rpg.util.RandomGenerator;

import org.junit.Before;
import org.junit.Test;

public class AttackHandlerTest {
	// class under test
	private AttackHandler handler;
	private IOUtils ioMock;
	private RandomGenerator randomUtilsMock;
	private Command cmdMock;
	private GameContext contextMock;

	@Before
	public void setup() {
		cmdMock = mock(Command.class);
		contextMock = mock(GameContext.class, RETURNS_DEEP_STUBS);
		ioMock = mock(IOUtils.class);
		randomUtilsMock = mock(RandomGenerator.class);
		handler = new AttackHandler(ioMock, randomUtilsMock);
	}

	@Test
	public void playerCharacterShouldNotAttackWhenNoEnemyExist() {

	}

	@Test
	public void playerCharacterShouldNotAttackWhenEnemyIsDead() {
		// given
		given(contextMock.getWorld().getPlayerCharacterEnemy().isDead()).willReturn(true);

		// when
		handler.handle(cmdMock, contextMock);

		// then
		verify(contextMock.getWorld().getPlayerCharacterEnemy(), never()).takeDamage(anyInt());
	}

	@Test
	public void playerCharacterShouldAttackAndDontGiveAnyDamage() {
		// given
		given(contextMock.getWorld().hasPlayerCharacterEnemy()).willReturn(false);

		// when
		handler.handle(cmdMock, contextMock);

		// then
		verify(contextMock.getWorld().getPlayerCharacterEnemy(), never()).takeDamage(anyInt());
	}

	@Test
	public void playerCharacterShouldAttackAndGiveDamage() {
		// given
		final int damage = 5;
		given(randomUtilsMock.nextInt(anyInt())).willReturn(damage);

		EnemyCharacter enemy = mock(EnemyCharacter.class);
		given(contextMock.getWorld().hasPlayerCharacterEnemy()).willReturn(true);
		given(contextMock.getWorld().getPlayerCharacterEnemy()).willReturn(enemy);

		// when
		handler.handle(cmdMock, contextMock);

		// then
		verify(contextMock.getWorld().getPlayerCharacterEnemy()).takeDamage(damage);
	}

	@Test
	public void playerCharacterShouldAttackAndKillEnemy() {
		// given
		final int damage = 5;
		given(randomUtilsMock.nextInt(anyInt())).willReturn(damage);

		EnemyCharacter enemy = mock(EnemyCharacter.class);
		given(enemy.isDead()).willReturn(false, true);
		
		given(contextMock.getWorld().hasPlayerCharacterEnemy()).willReturn(true);
		given(contextMock.getWorld().getPlayerCharacterEnemy()).willReturn(enemy);

		// when
		handler.handle(cmdMock, contextMock);

		// then
		verify(contextMock.getWorld().getPlayerCharacterEnemy()).takeDamage(damage);
		verify(contextMock.getWorld()).discardPlayerCharacterEnemy();
	}

}
