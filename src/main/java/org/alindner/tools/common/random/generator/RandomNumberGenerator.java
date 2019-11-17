package org.alindner.tools.common.random.generator;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.stream.IntStream;

public class RandomNumberGenerator implements IRandomStringGenerator<BigInteger> {
	private final java.util.Random random;
	private final char[]           symbols;

	public RandomNumberGenerator(final String alphanum) {
		this(alphanum, new SecureRandom());
	}

	public RandomNumberGenerator(final String alphanum, final java.util.Random random) {

		if (alphanum.length() < 2) {
			throw new IllegalArgumentException();
		}
		this.symbols = alphanum.toCharArray();
		this.random = Objects.requireNonNull(random);
	}

	@Override
	public BigInteger next(final int length) {
		if (length < 1) {
			throw new IllegalArgumentException();
		}
		return new BigInteger(
				IntStream.range(0, length)
				         .mapToObj(i -> String.valueOf(this.symbols[this.random.nextInt(this.symbols.length)]))
				         .reduce(String::concat)
				         .orElse("0")
		);
	}
}
