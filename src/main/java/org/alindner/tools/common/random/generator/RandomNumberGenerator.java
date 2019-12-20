package org.alindner.tools.common.random.generator;

import java.math.BigInteger;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class RandomNumberGenerator implements IRandomStringGenerator<BigInteger> {
	private final java.util.Random random;
	private final char[]           symbols;

	public RandomNumberGenerator(final Generator.ICharPool alphanum) {
		this(alphanum, ThreadLocalRandom.current());
	}

	public RandomNumberGenerator(final Generator.ICharPool alphanum, final Random random) {
		this.symbols = alphanum.getCharacters();
		this.random = Objects.requireNonNull(random);
	}

	@Override
	public BigInteger next(final int length) {
		return new BigInteger(
				IntStream.range(0, length)
				         .mapToObj(i -> String.valueOf(this.symbols[this.random.nextInt(this.symbols.length)]))
				         .reduce(String::concat)
				         .orElse("0")
		);
	}
}
