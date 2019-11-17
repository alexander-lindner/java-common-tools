package org.alindner.tools.common.random;

import org.alindner.tools.common.random.generator.Generator;
import org.alindner.tools.common.random.generator.RandomNumberGenerator;
import org.alindner.tools.common.random.generator.RandomStringGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
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
				.build(random -> new RandomStringGenerator("89", random))
				.stream(5, this.count)
				.forEach(s -> assertFalse(s.contains("[a-zA-Z0-7]+")));
		Random
				.build(random -> new RandomNumberGenerator("89", random))
				.stream(5, this.count)
				.forEach(Assertions::assertNotNull);
	}

	@Test
	void customGeneratorWithRandomizer() {
		Random
				.build(random -> new RandomStringGenerator("89", random), new java.util.Random())
				.stream(5, this.count)
				.collect(Collectors.toList())
				.forEach(s -> assertFalse(s.contains("[a-zA-Z0-7]+")));
	}

	@Test
	void stream() {
		final List<String> keys = new ArrayList<>();
		final int          size = 100;
		Random.StringRandoms.stream(size, o -> !keys.contains(o)).forEach(keys::add);
		assertEquals(size, keys.size());
		keys.clear();
		Random.StringRandoms.stream(size, this.keyLength, o -> !keys.contains(o)).forEach(keys::add);
		assertEquals(size, keys.size());
		keys.forEach(s -> assertTrue(s.length() >= this.keyLength));

		Assertions.assertEquals(this.count, Random.build(Generator.alphanumeric()).stream().limit(this.count).count());
	}

	@Test
	void streamParallel() {
		final List<String> keys = Random.StringRandoms.streamParallel(this.count).collect(Collectors.toList());
		assertEquals(this.count, keys.size());

		final List<String> keys2 = Random.StringRandoms
				.streamParallel(this.count, this.keyLength)
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
		final Set<String> keys = Random.StringRandoms.streamParallelUnique(this.count, this.keyLength)
		                                             .collect(Collectors.toSet());
		assertEquals(this.count, keys.size());

		final Set<String> keys3 = Random.StringRandoms.streamParallelUnique(this.count).collect(Collectors.toSet());
		assertEquals(this.count, keys3.size());

		final Set<String> keys2 = Random.StringRandoms.streamParallelUnique(
				this.count,
				this.keyLength,
				s -> !s.startsWith("a")
		).collect(Collectors.toSet());
		assertEquals(this.count, keys2.size());
		keys2.forEach(s -> assertFalse(s.startsWith("a")));

		assertEquals(
				this.count,
				Random.build(Generator.alphanumeric())
				      .parallelUniqueStream(this.count, this.keyLength)
				      .count()
		);


		final Set<String> keys4 = Random.build(Generator.alphanumeric())
		                                .parallelUniqueStream(this.count, this.keyLength, s -> s.startsWith("a"))
		                                .collect(Collectors.toSet());
		assertEquals(this.count, keys4.size());
		keys4.forEach(s -> assertTrue(s.startsWith("a")));
	}

	@Test
	void list() {
		final List<String> list = Random.StringRandoms.list(this.count, this.keyLength, s -> true);
		assertEquals(this.count, list.size());
		list.forEach(s -> assertTrue(s.length() >= this.keyLength));
		final List<String> list2 = Random.list(
				this.count,
				this.keyLength,
				s -> s.startsWith("a"),
				random -> new RandomStringGenerator("abc")
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
	}

	@Test
	void testBuild() {
	}

	@Test
	void testBuild1() {
	}
}