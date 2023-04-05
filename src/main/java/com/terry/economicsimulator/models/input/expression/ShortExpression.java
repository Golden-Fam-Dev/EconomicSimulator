package com.terry.economicsimulator.models.input.expression;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class ShortExpression {
  Short eq;
  Short ne;
  Short lt;
  Short lte;
  Short gt;
  Short gte;
  List<Short> in;
  List<Short> notIn;
}
