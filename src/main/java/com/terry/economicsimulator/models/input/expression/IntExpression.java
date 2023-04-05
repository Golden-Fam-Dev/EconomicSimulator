package com.terry.economicsimulator.models.input.expression;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class IntExpression {
  Integer eq;
  Integer ne;
  Integer lt;
  Integer lte;
  Integer gt;
  Integer gte;
  List<Integer> in;
  List<Integer> notIn;
}
