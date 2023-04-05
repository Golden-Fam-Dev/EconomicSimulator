package com.terry.economicsimulator.graphql.dto;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.util.Map;
import java.util.stream.Collectors;

@Value
@Builder
public class GraphQLModelWithFilter<T> {
  T model;

  @Singular
  Map<String, Object> filters;

  // Set to false if filters with a null value should be included in the constructed object
  @NonFinal
  @Builder.Default
  boolean ignoreNullFilters = true;

  GraphQLModelWithFilter(T model, Map<String, Object> filters, boolean ignoreNullFilters) {
    this.model = model;
    this.filters = ignoreNullFilters ? getNonNullFilters(filters) : filters;
    this.ignoreNullFilters = ignoreNullFilters;
  }

  private static Map<String, Object> getNonNullFilters(Map<String, Object> filters) {
    return filters.entrySet().stream().filter(e -> e.getValue() != null).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  public enum FilterKeys {
    LOCATION_FILTER_KEY,
    HOURS_OF_OPERATION_FILTER_KEY,
    ADDRESS_FILTER_KEY,
    LOCATION_ATTRIBUTE_VALUE_KEY,
    RELATED_LOCATION_FILTER_KEY
  }
}
