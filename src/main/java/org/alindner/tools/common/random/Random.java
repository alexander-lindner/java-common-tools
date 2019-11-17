package org.alindner.tools.common.random;

import org.alindner.tools.common.random.generator.Generator;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
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
		private final Generator.ICharPool<T> pool;
		private final java.util.Random       random;

		public RandomStream(final Generator.ICharPool<T> pool) {
			this.pool = pool;
			this.random = ThreadLocalRandom.current();
		}

		public RandomStream(final Generator.ICharPool<T> pool, final java.util.Random random) {
			this.pool = pool;
			this.random = random;
		}

		public Stream<T> stream() {
			return Stream.generate(() -> this.pool.getGenerator(this.random).next(5))
			             .unordered();
		}

		public Stream<T> stream(final int length) {
			return Stream.generate(() -> this.pool.getGenerator(this.random).next(length))
			             .unordered();
		}

		public Stream<T> stream(final int length, final int limit) {
			return Stream.generate(() -> this.pool.getGenerator(this.random).next(length))
			             .limit(limit);

		}

		public Stream<T> uniqueStream(final int length, final int limit) {
			return this.stream(length)
			           .distinct()
			           .limit(limit);
		}

		public Stream<T> uniqueStream(final int length, final int limit, final Predicate<T> filter) {
			return this.stream(length)
			           .filter(filter)
			           .distinct()
			           .limit(limit);
		}

		public Stream<T> parallelStream(final int length, final int limit) {
			return this.stream(length)
			           .parallel()
			           .limit(limit);
		}

		public Stream<T> parallelStream(final int length, final int limit, final Predicate<T> filter) {
			return this.stream(length)
			           .parallel()
			           .filter(filter)
			           .limit(limit);
		}

		public Stream<T> parallelUniqueStream(final int count, final int length) {
			return this.stream(length)
			           .parallel()
			           .distinct()
			           .limit(count);
		}

		public Stream<T> parallelUniqueStream(final int count, final int length, final Predicate<T> filter) {
			return this.stream(length)
			           .parallel()
			           .distinct()
			           .filter(filter)
			           .limit(count);
		}
	}

	/*** STATIC ****/

	static class StringRandoms {
		public static Stream<String> stream(final int limit, final Predicate<String> method) {
			return StringRandoms.stream(limit, 5, method);
		}

		public static Stream<String> stream(final int limit, final int length, final Predicate<String> method) {
			return Random.stream(limit, length, method, Generator.alphanumeric());
		}

		public static Stream<String> streamParallel(final int count) {
			return StringRandoms.streamParallel(count, 5);
		}

		public static Stream<String> streamParallel(final int count, final int length) {
			return Random.streamParallel(count, length, Generator.alphanumeric());
		}

		public static Stream<String> streamParallelUnique(final int count) {
			return StringRandoms.streamParallelUnique(count, 5);
		}

		public static Stream<String> streamParallelUnique(final int count, final int length) {
			return StringRandoms.streamParallelUnique(count, length, (String str) -> true);
		}

		public static Stream<String> streamParallelUnique(final int count, final int length, final Predicate<String> method) {
			return Random.streamParallelUnique(count, length, method, Generator.alphanumeric());
		}

		public static List<String> list(final int count, final int length, final Predicate<String> method) {
			return StringRandoms.stream(count, length, method).collect(Collectors.toList());
		}
	}

	public static <T> Stream<T> stream(final int limit, final int length, final Predicate<? super T> method, final Generator.ICharPool<T> generator) {
		return Random.build(generator)
		             .stream(length)
		             .filter(method)
		             .limit(limit);
	}

	public static <T> Stream<T> streamParallel(final int count, final int length, final Generator.ICharPool<T> generator) {
		return Random.build(generator).stream(length, count);
	}

	public static <T> Stream<T> streamParallelUnique(final int count, final int length, final Predicate<? super T> method, final Generator.ICharPool<T> generator) {
		return Random.build(generator).stream(length).distinct().filter(method).limit(count);
	}

	public static <T> List<T> list(final int count, final int length, final Predicate<? super T> method, final Generator.ICharPool<T> generator) {
		return Random.stream(count, length, method, generator).collect(Collectors.toList());
	}

	public static String get(final int length) {
		return Random.build().stream(length).findFirst().orElseThrow(RuntimeException::new);
	}

	public static BigInteger getNumber(final int length) {
		return Random.build(Generator.integerNumbers()).stream(length).findFirst().orElseThrow(RuntimeException::new);
	}

	/**
	 * Build a  stream based on a given char pool
	 *
	 * @return stream of random strings
	 */
	public static RandomStream<String> build() {
		return new RandomStream<>(Generator.alphanumeric());
	}

	/**
	 * Build a  stream based on a given char pool
	 *
	 * @param pool char pool
	 * @param <T>  type of result
	 *
	 * @return stream of random strings
	 */
	public static <T> RandomStream<T> build(final Generator.ICharPool<T> pool) {
		return new RandomStream<>(pool);
	}

	/**
	 * Build a  stream based on a given char pool
	 *
	 * @param pool   char pool
	 * @param random random generator
	 * @param <T>    type of result
	 *
	 * @return stream of random strings
	 */
	public static <T> RandomStream<T> build(final Generator.ICharPool<T> pool, final java.util.Random random) {
		return new RandomStream<>(pool, random);
	}
}