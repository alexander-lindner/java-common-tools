package org.alindner.tools.common.random;

import org.alindner.tools.common.random.generator.Generator;
import org.alindner.tools.common.random.generator.IRandomStringGenerator;

import java.math.BigInteger;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is a {@link Stream} based implementation for random string generation.
 */
public class Random {
	/**
	 * create a {@link Stream} by proving a generator
	 *
	 * @param <T> type of the result
	 */
	static class RandomStream<T> {
		private final IRandomStringGenerator<T> generator;

		public RandomStream(final IRandomStringGenerator<T> generator) {
			this.generator = generator;
		}


		public Stream<T> stream() {
			return Stream.generate(() -> this.generator.next(5))
			             .unordered();
		}

		public Stream<T> stream(final int length) {
			Random.checkLength(length);
			return Stream.generate(() -> this.generator.next(length))
			             .unordered();
		}

		public Stream<T> stream(final int length, final int limit) {
			Random.checkLength(length);
			return Stream.generate(() -> this.generator.next(length))
			             .limit(limit);

		}

		public Stream<T> stream(final int length, final int limit, final Predicate<T> filter) {
			Random.checkLength(length);
			return Stream.generate(() -> this.generator.next(length))
			             .filter(filter)
			             .limit(limit);

		}

		public Stream<T> uniqueStream() {
			return this.stream()
			           .distinct();
		}

		public Stream<T> uniqueStream(final int length, final int limit) {
			Random.checkLength(length);
			return this.stream(length)
			           .distinct()
			           .limit(limit);
		}

		public Stream<T> uniqueStream(final int length, final int limit, final Predicate<T> filter) {
			Random.checkLength(length);
			return this.stream(length)
			           .filter(filter)
			           .distinct()
			           .limit(limit);
		}

		public Stream<T> parallelStream(final int length, final int limit) {
			Random.checkLength(length);
			return this.stream(length)
			           .parallel()
			           .limit(limit);
		}

		public Stream<T> parallelStream(final int length, final int limit, final Predicate<T> filter) {
			Random.checkLength(length);
			return this.stream(length)
			           .parallel()
			           .filter(filter)
			           .limit(limit);
		}

		public Stream<T> parallelUniqueStream(final int length, final int count) {
			Random.checkLength(length);
			return this.stream(length)
			           .parallel()
			           .distinct()
			           .limit(count);
		}

		public Stream<T> parallelUniqueStream(final int length, final int count, final Predicate<T> filter) {
			Random.checkLength(length);
			return this.stream(length)
			           .parallel()
			           .distinct()
			           .filter(filter)
			           .limit(count);
		}

		public List<T> list(final int length, final int count, final Predicate<T> filter) {
			return this.stream(length, count, filter).collect(Collectors.toList());

		}
	}

	/**
	 * Prebuild String stream with alphanumeric chars
	 *
	 * @see Generator#alphanumeric()
	 * @see Generator.AlphaNumericPool
	 */
	static class StringRandoms {
		/**
		 * get a basic endless String stream with alphanumeric chars
		 *
		 * @return alphanumeric Stream
		 */
		public static Stream<java.lang.String> stream() {
			return Random.build().stream();
		}

		/**
		 * get a basic String stream with alphanumeric chars
		 *
		 * @param limit number of strings
		 *
		 * @return alphanumeric Stream
		 */
		public static Stream<java.lang.String> stream(final int limit) {
			return StringRandoms.stream(limit, s -> true);
		}

		/**
		 * get a basic String stream with alphanumeric chars
		 *
		 * @param limit  number of strings
		 * @param length length of each random string
		 *
		 * @return alphanumeric Stream
		 */
		public static Stream<java.lang.String> stream(final int limit, final int length) {
			return StringRandoms.stream(limit, length, s -> true);
		}

		/**
		 * get a basic String stream with alphanumeric chars
		 *
		 * @param limit  number of strings
		 * @param method filter method which is applied before the stream is limited
		 *
		 * @return alphanumeric Stream
		 */
		public static Stream<java.lang.String> stream(final int limit, final Predicate<java.lang.String> method) {
			return StringRandoms.stream(limit, 5, method);
		}

		/**
		 * get a basic String stream with alphanumeric chars
		 *
		 * @param limit  number of strings
		 * @param length length of each random string
		 * @param method filter method which is applied before the stream is limited
		 *
		 * @return alphanumeric Stream
		 */
		public static Stream<java.lang.String> stream(final int limit, final int length, final Predicate<java.lang.String> method) {
			return Random.stream(limit, length, method, Generator.alphanumeric());
		}


		/**
		 * get a basic endless String stream with alphanumeric chars
		 *
		 * @return alphanumeric Stream
		 */
		public static Stream<java.lang.String> uniqueStream() {
			return Random.build().uniqueStream();
		}

		/**
		 * get a basic String stream with alphanumeric chars
		 *
		 * @param limit number of strings
		 *
		 * @return alphanumeric Stream
		 */
		public static Stream<java.lang.String> uniqueStream(final int limit) {
			return StringRandoms.uniqueStream(limit, s -> true);
		}

		/**
		 * get a basic String stream with alphanumeric chars
		 *
		 * @param limit  number of strings
		 * @param length length of each random string
		 *
		 * @return alphanumeric Stream
		 */
		public static Stream<java.lang.String> uniqueStream(final int limit, final int length) {
			return StringRandoms.uniqueStream(limit, length, s -> true);
		}

		/**
		 * get a basic String stream with alphanumeric chars
		 *
		 * @param limit  number of strings
		 * @param method filter method which is applied before the stream is limited
		 *
		 * @return alphanumeric Stream
		 */
		public static Stream<java.lang.String> uniqueStream(final int limit, final Predicate<java.lang.String> method) {
			return StringRandoms.uniqueStream(limit, 5, method);
		}

		/**
		 * get a basic String stream with alphanumeric chars
		 *
		 * @param limit  number of strings
		 * @param length length of each random string
		 * @param filter filter method which is applied before the stream is limited
		 *
		 * @return alphanumeric Stream
		 */
		public static Stream<java.lang.String> uniqueStream(final int limit, final int length, final Predicate<java.lang.String> filter) {
			return Random.build().uniqueStream(limit, length, filter);
		}

		/**
		 * get a parallel String stream with alphanumeric chars
		 *
		 * @param limit number of strings
		 *
		 * @return parallel alphanumeric Stream
		 */
		public static Stream<java.lang.String> streamParallel(final int limit) {
			return StringRandoms.streamParallel(limit, 5);
		}

		/**
		 * get a parallel String stream with alphanumeric chars
		 *
		 * @param limit  number of strings
		 * @param length length of each random string
		 *
		 * @return parallel alphanumeric Stream
		 */
		public static Stream<java.lang.String> streamParallel(final int limit, final int length) {
			return Random.streamParallel(limit, length, Generator.alphanumeric());
		}

		/**
		 * get a parallel stream with unique String based on alphanumeric chars
		 *
		 * @param limit number of strings
		 *
		 * @return parallel unique stream
		 */
		public static Stream<java.lang.String> streamParallelUnique(final int limit) {
			return StringRandoms.streamParallelUnique(limit, 5);
		}

		/**
		 * get a parallel stream with unique String based on alphanumeric chars
		 *
		 * @param limit  number of strings
		 * @param length length of each random string
		 *
		 * @return parallel unique stream
		 */
		public static Stream<java.lang.String> streamParallelUnique(final int limit, final int length) {
			return StringRandoms.streamParallelUnique(limit, length, (java.lang.String str) -> true);
		}

		/**
		 * get a parallel stream with unique String based on alphanumeric chars
		 *
		 * @param limit  number of strings
		 * @param length length of each random string
		 * @param filter filter method which is applied before the stream is limited
		 *
		 * @return parallel unique stream
		 */
		public static Stream<java.lang.String> streamParallelUnique(final int limit, final int length, final Predicate<java.lang.String> filter) {
			return Random.streamParallelUnique(limit, length, filter, Generator.alphanumeric());
		}

		/**
		 * get a list of random strings
		 *
		 * @param limit  number of strings
		 * @param length length of each random string
		 * @param filter filter method which is applied before the stream is limited
		 *
		 * @return list of strings
		 */
		public static List<java.lang.String> list(final int limit, final int length, final Predicate<java.lang.String> filter) {
			return StringRandoms.stream(limit, length, filter).collect(Collectors.toList());
		}
	}

	public static class String {
		/**
		 * Build a  stream based on a given generator
		 *
		 * @return stream of random strings
		 */
		public static RandomStream<java.lang.String> build() {
			return new RandomStream<>(Generator.alphanumeric());
		}
	}

	/**
	 * generates a limited filtered stream of random strings based on an given generator
	 *
	 * @param limit     number of strings
	 * @param length    length of each random string
	 * @param filter    filter method which is applied before the stream is limited
	 * @param generator generator
	 * @param <T>       type of strings
	 *
	 * @return limited stream
	 */
	public static <T> Stream<T> stream(final int limit, final int length, final Predicate<? super T> filter, final IRandomStringGenerator<T> generator) {
		return Random.build(generator)
		             .stream(length)
		             .filter(filter)
		             .limit(limit);
	}

	/**
	 * @param limit     number of strings
	 * @param length    length of each random string
	 * @param generator generator
	 * @param <T>       type of strings
	 *
	 * @return
	 */
	public static <T> Stream<T> streamParallel(final int limit, final int length, final IRandomStringGenerator<T> generator) {
		return Random.build(generator).stream(length, limit);
	}

	public static <T> Stream<T> streamParallelUnique(final int count, final int length, final Predicate<? super T> method, final IRandomStringGenerator<T> generator) {
		return Random.build(generator)
		             .stream(length)
		             .distinct()
		             .filter(method)
		             .limit(count);
	}

	public static <T> List<T> list(final int count, final int length, final Predicate<? super T> method, final IRandomStringGenerator<T> generator) {
		return Random.stream(count, length, method, generator).collect(Collectors.toList());
	}

	public static java.lang.String get(final int length) {
		return Random.build()
		             .stream(length)
		             .findFirst()
		             .orElseThrow(RuntimeException::new);
	}

	public static BigInteger getNumber(final int length) {
		return Random.build(Generator.integerNumbers())
		             .stream(length)
		             .findFirst()
		             .orElseThrow(RuntimeException::new);
	}

	/**
	 * Build a  stream based on a given char pool
	 *
	 * @return stream of random strings
	 */
	public static RandomStream<java.lang.String> build() {
		return new RandomStream<>(Generator.alphanumeric());
	}

	/**
	 * Build a  stream based on a given generator
	 *
	 * @param generator char pool
	 * @param <T>       type of result
	 *
	 * @return stream of random strings
	 */
	public static <T> RandomStream<T> build(final IRandomStringGenerator<T> generator) {
		return new RandomStream<>(generator);
	}

	/**
	 * Checks the minimum length
	 *
	 * @param length length
	 *
	 * @throws IllegalArgumentException if length is lower 2
	 */
	private static void checkLength(final int length) throws IllegalArgumentException {
		if (length < 2) {
			throw new IllegalArgumentException();
		}
	}
}