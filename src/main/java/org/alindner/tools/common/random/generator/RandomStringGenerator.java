package org.alindner.tools.common.random.generator;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.stream.IntStream;

public class RandomStringGenerator implements IRandomStringGenerator<String> {
	private final java.util.Random random;
	private final char[]           symbols;

	/**
	 * Create an alphanumeric strings from a secure generator.
	 *
	 * @param alphanum chars
	 */
	public RandomStringGenerator(final String alphanum) {
		this(alphanum, new SecureRandom());
	}

	/**
	 * Create an alphanumeric strings from a secure generator.
	 *
	 * @param alphanum chars
	 * @param random   random generator
	 */
	public RandomStringGenerator(final String alphanum, final java.util.Random random) {
		if (alphanum.length() < 2) {
			throw new IllegalArgumentException();
		}
		this.symbols = alphanum.toCharArray();
		this.random = Objects.requireNonNull(random);
	}

	/**
	 * Generate a random string.
	 */
	@Override
	public String next(final int length) {
		if (length < 1) {
			throw new IllegalArgumentException();
		}

		final char[] buf = new char[length];
		IntStream.range(0, buf.length)
		         .forEach(i -> buf[i] = this.symbols[this.random.nextInt(this.symbols.length)]);
		return new String(buf);
	}
}
