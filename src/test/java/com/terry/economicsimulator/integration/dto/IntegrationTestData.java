package com.terry.economicsimulator.integration.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class IntegrationTestData {
  // Brief description of the test case
  String testCase;

  // Acceptance criteria from the story which the test was built off of
  String acceptanceCriteria;

  // Data to load into the database (INSERT queries)
 List <String> dbDataFileName;

  // Request Data used to send the request into the API
  String request;

  // Data to validate against response
  String response;

  // Data Validations to perform on the database
  List<DataValidation> dbDataValidation;
}