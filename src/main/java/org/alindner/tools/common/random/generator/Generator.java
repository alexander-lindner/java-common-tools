package org.alindner.tools.common.random.generator;

import java.math.BigInteger;
import java.util.Locale;
import java.util.Random;

public class Generator {
	public interface ICharPool<T> {
		IRandomStringGenerator<T> getGenerator(Random random);
	}

	/**
	 * an alphanumeric string generator.
	 */
	public static class AlphaNumericPool implements ICharPool<String> {
		final String upper    = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final String lower    = this.upper.toLowerCase(Locale.ROOT);
		final String digits   = "0123456789";
		final String alphanum = this.upper + this.lower + this.digits;

		@Override
		public IRandomStringGenerator<String> getGenerator(final Random random) {
			return new RandomStringGenerator(this.alphanum, random);
		}
	}

	public static class Numbers implements ICharPool<String> {
		final String digits = "0123456789";

		@Override
		public IRandomStringGenerator<String> getGenerator(final Random random) {
			return new RandomStringGenerator(this.digits, random);
		}
	}

	public static class IntegerNumbers implements ICharPool<BigInteger> {
		final String digits = "0123456789";

		@Override
		public IRandomStringGenerator<BigInteger> getGenerator(final Random random) {
			return new RandomNumberGenerator(this.digits, random);
		}
	}

	/**
	 * Create an alphanumeric string generator.
	 *
	 * @return String Generator
	 */
	public static AlphaNumericPool alphanumeric() {
		return new AlphaNumericPool();
	}

	/**
	 * Create an alphanumeric string generator.
	 *
	 * @return Number Generator
	 */
	public static Numbers numbers() {
		return new Numbers();
	}

	/**
	 * Create an number saved as {@link BigInteger} generator.
	 *
	 * @return Integer Number Generator
	 */
	public static IntegerNumbers integerNumbers() {
		return new IntegerNumbers();
	}
}
