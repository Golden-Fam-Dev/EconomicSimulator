package com.terry.economicsimulator.models.input.expression;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Value
@Builder
@Jacksonized
public class DateExpression {
  LocalDate eq;
  LocalDate ne;
  LocalDate lt;
  LocalDate lte;
  LocalDate gt;
  LocalDate gte;
}
