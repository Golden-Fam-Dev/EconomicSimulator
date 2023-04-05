package com.terry.economicsimulator.models.input.expression;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalTime;

@Value
@Builder
@Jacksonized
public class LocalTimeExpression {
  LocalTime eq;
  LocalTime ne;
  LocalTime lt;
  LocalTime lte;
  LocalTime gt;
  LocalTime gte;
}
