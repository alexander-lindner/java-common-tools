package org.alindner.tools.common.random.generator;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class RandomStringGenerator implements IRandomStringGenerator<String> {
	private final java.util.Random random;
	private final char[]           symbols;

	/**
	 * Create an alphanumeric strings from a secure generator.
	 *
	 * @param alphanum chars
	 */
	public RandomStringGenerator(final Generator.ICharPool alphanum) {
		this(alphanum, ThreadLocalRandom.current());
	}

	/**
	 * Create an alphanumeric strings from a secure generator.
	 *
	 * @param alphanum chars
	 * @param random   random generator
	 */
	public RandomStringGenerator(final Generator.ICharPool alphanum, final java.util.Random random) {
		this.symbols = alphanum.getCharacters();
		this.random = Objects.requireNonNull(random);
	}

	/**
	 * Generate a random string.
	 */
	@Override
	public String next(final int length) {
		return IntStream.range(0, length)
		                .mapToObj(i -> String.valueOf(this.symbols[this.random.nextInt(this.symbols.length)]))
		                .reduce(String::concat)
		                .orElse("");
	}
}
