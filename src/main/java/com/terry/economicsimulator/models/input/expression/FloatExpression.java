package com.terry.economicsimulator.models.input.expression;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class FloatExpression {
  Float eq;
  Float ne;
  Float lt;
  Float lte;
  Float gt;
  Float gte;
  List<Float> in;
  List<Float> notIn;
}
