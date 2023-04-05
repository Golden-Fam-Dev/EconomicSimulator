package com.terry.economicsimulator.models.input.expression;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.OffsetTime;

@Value
@Builder
@Jacksonized
public class TimeExpression {
  OffsetTime eq;
  OffsetTime ne;
  OffsetTime lt;
  OffsetTime lte;
  OffsetTime gt;
  OffsetTime gte;
}
