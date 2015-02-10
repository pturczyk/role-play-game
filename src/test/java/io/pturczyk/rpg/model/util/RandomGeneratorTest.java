package io.pturczyk.rpg.model.util;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.pturczyk.rpg.util.RandomGenerator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)  
public class RandomGeneratorTest {

    private int limit;

	public RandomGeneratorTest(int limit) {
		this.limit = limit;
    }  

	@Parameters
	public static Collection<Object[]> generateParams() {
		List<Object[]> params = new ArrayList<Object[]>();
		for (int limit = 2; limit <= 10; limit++) {
			params.add(new Object[] { limit });
		}
		return params;
	}

	// Test different limit values
	@Test
	public void shouldReturnRandomLimitedFromTop() {
		// given
		RandomGenerator random = new RandomGenerator();
		
		// when
		int randomInt = random.nextInt(limit);
		
		// then
		assertThat(randomInt).isGreaterThanOrEqualTo(0).isLessThanOrEqualTo(limit);
	}
}
