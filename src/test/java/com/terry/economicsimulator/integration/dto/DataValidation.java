package com.terry.economicsimulator.integration.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class DataValidation {
  // Query to run which can pull back data to validate against
  String query;

  // List of data to validate against.  Inner list represents each column of the result set
  List<List<Object>> data;
}
