package org.alindner.tools.common.random;

import org.alindner.tools.common.random.generator.Generator;
import org.alindner.tools.common.random.generator.RandomNumberGenerator;
import org.alindner.tools.common.random.generator.RandomStringGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class RandomTest {
	private final int keyLength = 15;
	private final int count     = 1000;

	@Test
	void builder() {
		final List<String> list = Random
				.build(Generator.numbers())
				.stream(5, this.count)
				.collect(Collectors.toList());
		assertEquals(this.count, list.size());
		list.forEach(s -> assertFalse(s.contains("[a-zA-Z]+")));
		list.forEach(s -> assertEquals(5, s.length()));
	}

	@Test
	void customGenerator() {
		Random
				.build(Generator.alphanumeric())
				.stream(5, this.count)
				.forEach(Assertions::assertNotNull);
		Random
				.build(new RandomNumberGenerator("89"::toCharArray))
				.stream(5, this.count)
				.forEach(Assertions::assertNotNull);
		Random
				.build(new RandomStringGenerator("89"::toCharArray, new SecureRandom()))
				.stream(5, 8)
				.forEach(Assertions::assertNotNull);
	}

	@Test
	void customGeneratorWithRandomizer() {
		Random
				.build(new RandomStringGenerator("89"::toCharArray, new java.util.Random()))
				.stream(5, this.count)
				.collect(Collectors.toList())
				.forEach(s -> assertFalse(s.contains("[a-zA-Z0-7]+")));

		Random.String.build().stream(8, 5, s -> s.startsWith("bb") && s.endsWith("bb"))
		             .forEach(s -> assertTrue(s.startsWith("bb") && s.endsWith("bb")));
		Random
				.build(length -> "aa" + Generator.alphanumeric().next(length - 4) + "aa")
				.stream(13, 8)
				.forEach(s -> assertTrue(s.startsWith("aa") && s.endsWith("aa")));
	}

	@Test
	void stream() {
		final List<String> keys = new ArrayList<>();
		final int          size = 100;
		Random.String.build().stream(this.keyLength, size, o -> !keys.contains(o)).forEach(keys::add);
		assertEquals(size, keys.size());
		keys.clear();
		Random.String.build().stream(this.keyLength, size, o -> !keys.contains(o)).forEach(keys::add);
		assertEquals(size, keys.size());
		keys.forEach(s -> assertTrue(s.length() >= this.keyLength));

		Assertions.assertEquals(this.count, Random.build(Generator.alphanumeric()).stream().limit(this.count).count());
	}

	@Test
	void streamParallel() {
		final List<String> keys = Random.String
				.build()
				.parallelStream(this.keyLength, this.count)
				.collect(Collectors.toList());
		assertEquals(this.count, keys.size());

		final List<String> keys2 = Random.String.build()
		                                        .parallelStream(this.keyLength, this.count)
		                                        .collect(Collectors.toList());
		assertEquals(this.count, keys2.size());
		keys2.forEach(s -> assertTrue(s.length() >= this.keyLength));

		assertEquals(
				this.count,
				Random.build(Generator.alphanumeric())
				      .parallelStream(this.keyLength, this.count)
				      .count()
		);

		final Set<String> keys4 = Random.build(Generator.alphanumeric())
		                                .parallelStream(this.keyLength, this.count, s -> s.startsWith("a"))
		                                .collect(Collectors.toSet());
		assertEquals(this.count, keys4.size());
		keys4.forEach(s -> assertTrue(s.startsWith("a")));
	}

	@Test
	void streamUnique() {
		assertEquals(
				this.count,
				Random.build(Generator.alphanumeric())
				      .uniqueStream(this.keyLength, this.count)
				      .count()
		);


		final Set<String> keys4 = Random.build(Generator.alphanumeric())
		                                .uniqueStream(this.keyLength, this.count, s -> s.startsWith("a"))
		                                .collect(Collectors.toSet());
		assertEquals(this.count, keys4.size());
		keys4.forEach(s -> assertTrue(s.startsWith("a")));
	}

	@Test
	void streamParallelUnique() {
		final Set<String> keys = Random.String.build()
		                                      .parallelUniqueStream(this.keyLength, this.count)
		                                      .collect(Collectors.toSet());
		assertEquals(this.count, keys.size());

		final Set<String> keys3 = Random.String.build()
		                                       .parallelUniqueStream(this.keyLength, this.count)
		                                       .collect(Collectors.toSet());
		assertEquals(this.count, keys3.size());

		final Set<String> keys2 = Random.String.build()
		                                       .parallelUniqueStream(
				                                       this.keyLength,
				                                       this.count,
				                                       s -> !s.startsWith("a")
		                                       )
		                                       .collect(Collectors.toSet());
		assertEquals(this.count, keys2.size());
		keys2.forEach(s -> assertFalse(s.startsWith("a")));

		assertEquals(
				this.count,
				Random.build(Generator.alphanumeric())
				      .parallelUniqueStream(this.keyLength, this.count)
				      .count()
		);


		final Set<String> keys4 = Random.build(Generator.alphanumeric())
		                                .parallelUniqueStream(this.keyLength, this.count, s -> s.startsWith("a"))
		                                .collect(Collectors.toSet());
		assertEquals(this.count, keys4.size());
		keys4.forEach(s -> assertTrue(s.startsWith("a")));
	}

	@Test
	void list() {
		final List<String> list = Random.String.build().list(this.keyLength, this.count, s -> true);
		assertEquals(this.count, list.size());
		list.forEach(s -> assertTrue(s.length() >= this.keyLength));
		final List<String> list2 = Random.list(
				this.keyLength,
				this.count,
				s -> s.startsWith("a"),
				new RandomStringGenerator("abc"::toCharArray)
		);
		list2.forEach(s -> assertTrue(s.startsWith("a")));
	}

	@Test
	void get() {
		assertFalse(Random.get(5).isEmpty());
	}

	@Test
	void getNumber() {
		assertNotNull(Random.getNumber(5));
	}

	@Test
	void build() {
		final int keyLength = 50;
		final int count     = 1000000;
		final List<String> keys = Random.build(Generator.alphanumeric())
		                                .parallelUniqueStream(keyLength, count)
		                                .filter(s -> s.startsWith("a"))
		                                .collect(Collectors.toList());
		keys.forEach(s -> assertTrue(s.startsWith("a")));
	}

	@Test
	void exception() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> Random.build().stream(1));
		Assertions.assertThrows(IllegalArgumentException.class, () -> Random.build().stream(0));
		Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> Random.stream(5, 0, Objects::nonNull, Generator.alphanumeric())
		);
	}

	@Test
	void Strings() {
		Random.String.build().stream(13, 8, s -> s.startsWith("a")).forEach(System.out::println);
		Random.String.build().stream(13, 8).forEach(System.out::println);
//		Random.String.build().stream(13).forEach(System.out::println);
	}
}