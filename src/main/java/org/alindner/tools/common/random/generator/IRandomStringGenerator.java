package org.alindner.tools.common.random.generator;

/**
 * Interface for a random string generator. Provide a constructor where the randomness can be influenced.
 *
 * @param <T> type of string representation, mostly {@link String}.
 */
public interface IRandomStringGenerator<T> {
	/**
	 * provides a random string with length {@code length}.
	 *
	 * @param length length of string
	 *
	 * @return random string
	 */
	T next(int length);
}
