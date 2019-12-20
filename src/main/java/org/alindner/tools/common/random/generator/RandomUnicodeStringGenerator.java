package org.alindner.tools.common.random.generator;

import java.util.Objects;
import java.util.stream.IntStream;

public class RandomUnicodeStringGenerator implements IRandomStringGenerator<String> {
	private final java.util.Random random;


	/**
	 * Create an alphanumeric strings from a secure generator.
	 *
	 * @param random random generator
	 */
	public RandomUnicodeStringGenerator(final java.util.Random random) {
		this.random = Objects.requireNonNull(random);
	}

	/**
	 * Generate a random string.
	 */
	@Override
	public String next(final int length) {
		final char[] buf = new char[length];
		return IntStream.range(0, buf.length)
		                .map(i -> this.random.nextInt(65536))
		                .filter(Character::isDefined)
		                .mapToObj(i -> (char) i)
		                .map(value -> Character.toString(value))
		                .reduce(String::concat)
		                .orElse("");
	}
}
