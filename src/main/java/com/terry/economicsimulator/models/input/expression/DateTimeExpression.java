package com.terry.economicsimulator.models.input.expression;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.OffsetDateTime;

@Value
@Builder
@Jacksonized
public class DateTimeExpression {
  OffsetDateTime eq;
  OffsetDateTime ne;
  OffsetDateTime lt;
  OffsetDateTime lte;
  OffsetDateTime gt;
  OffsetDateTime gte;
}
