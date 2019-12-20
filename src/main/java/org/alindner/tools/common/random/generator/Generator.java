package org.alindner.tools.common.random.generator;

import java.math.BigInteger;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class Generator {
	public interface ICharPool {
		char[] getCharacters();
	}

	/**
	 * an alphanumeric string generator.
	 */
	public static class AlphaNumericPool implements ICharPool {
		final String upper    = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final String lower    = this.upper.toLowerCase(Locale.ROOT);
		final String digits   = "0123456789";
		final String alphanum = this.upper + this.lower + this.digits;

		@Override
		public char[] getCharacters() {
			return this.alphanum.toCharArray();
		}
	}

	/**
	 * an alphanumeric string generator.
	 */
	public static class AlphanumericSignsPool extends AlphaNumericPool {
		final String alphanum = super.alphanum + "A-Z,a-z,0-9,!§$%&/()=?{[]}\\-_.,:;+*~#<>";

		@Override
		public char[] getCharacters() {
			return this.alphanum.toCharArray();
		}
	}

	public static class NumbersPool implements ICharPool {
		final String digits = "0123456789";

		@Override
		public char[] getCharacters() {
			return this.digits.toCharArray();
		}
	}

	/**
	 * Create an alphanumeric string generator.
	 *
	 * @return String Generator
	 */
	public static IRandomStringGenerator<String> alphanumeric() {
		return new RandomStringGenerator(new AlphaNumericPool(), ThreadLocalRandom.current());
	}

	/**
	 * Create an alphanumeric string generator.
	 *
	 * @return String Generator
	 */
	public static IRandomStringGenerator<String> alphanumericSigns() {
		return new RandomStringGenerator(new AlphanumericSignsPool(), ThreadLocalRandom.current());
	}

	/**
	 * Create an alphanumeric string generator.
	 *
	 * @return String Generator
	 */
	public static IRandomStringGenerator<String> unicode() {
		return new RandomUnicodeStringGenerator(ThreadLocalRandom.current());
	}

	/**
	 * Create an alphanumeric string generator.
	 *
	 * @return Number Generator
	 */
	public static IRandomStringGenerator<String> numbers() {
		return new RandomStringGenerator(new NumbersPool(), ThreadLocalRandom.current());
	}

	/**
	 * Create an number saved as {@link BigInteger} generator.
	 *
	 * @return Integer Number Generator
	 */
	public static IRandomStringGenerator<BigInteger> integerNumbers() {
		return new RandomNumberGenerator(new NumbersPool(), ThreadLocalRandom.current());
	}
}
