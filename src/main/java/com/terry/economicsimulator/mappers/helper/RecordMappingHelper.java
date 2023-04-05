package com.terry.economicsimulator.mappers.helper;

import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class RecordMappingHelper {
	private RecordMappingHelper() {
		// Hide implicit constructor
	}

	/**
	 * Creates a one-to-many map of the given keys to a List of values.  Every key specified in the keys Set be added to
	 * the output map.  For any key which exists in the keys Set but not in the recordsMap, the key will be added to the
	 * output map with a value of an empty list.  All records of type R will be mapped to outputs of type O using the
	 * given mapperFunc
	 *
	 * @param keys       Known set of keys to create a map for
	 * @param recordsMap A lookup map of keys to a list of values.  This map may not contain every key that is in the keys Set
	 * @param mapperFunc Function which maps from a record of type <R> </R>
	 * @param <K>        Type of the keys
	 * @param <O>        Type of the mapped output
	 * @param <R>        Type of the input records
	 * @return Map of keys to List of mapped outputs
	 */
	public static <K, O, R> Map<K, List<O>> mapRecordsToList(Set<K> keys, Map<K, List<R>> recordsMap, Function<R, O> mapperFunc) {
		Assert.notNull(keys, "keys argument must be provided");
		Assert.notNull(recordsMap, "recordsMap argument must be provided");
		Assert.notNull(mapperFunc, "mapperFunc argument must be provided");

		return keys.stream()
				.collect(Collectors.toMap(
						Function.identity(),
						key -> recordsMap.getOrDefault(key, List.of()).stream() // key may not exist in the recordsMap
								.map(mapperFunc)
								.toList()
				));
	}

	/**
	 * Creates a one-to-many map of the given keys to an optional value.  Every key specified in the keys Set be added to
	 * the output map.  For any key which exists in the keys Set but not in the recordsMap, the key will be added to the
	 * output map with a value of an empty Optional.  All records of type R will be mapped to outputs of type O using the
	 * given mapperFunc
	 *
	 * @param keys       Known set of keys to create a map for
	 * @param recordsMap A lookup map of keys to a list of values.  This map may not contain every key that is in the keys Set
	 * @param mapperFunc Function which maps from a record of type <R> </R>
	 * @param <K>        Type of the keys
	 * @param <O>        Type of the mapped output
	 * @param <R>        Type of the input records
	 * @return Map of keys to List of mapped outputs
	 */
	public static <K, O, R> Map<K, Optional<O>> mapRecordsToOptional(Set<K> keys, Map<K, List<R>> recordsMap, Function<R, O> mapperFunc) {
		Assert.notNull(keys, "keys argument must be provided");
		Assert.notNull(recordsMap, "recordsMap argument must be provided");
		Assert.notNull(mapperFunc, "mapperFunc argument must be provided");

		return keys.stream()
				.collect(Collectors.toMap(
						Function.identity(),
						key -> Optional.ofNullable(recordsMap.get(key)) // key may not exist in the recordsMap
								.flatMap(records -> records.stream()
										.findFirst()  // Get the first record in the records list (as Optional)
										.map(mapperFunc)))); // If the first record exists, map using the mapperFunc; otherwise Optional.empty is returned
	}
}
