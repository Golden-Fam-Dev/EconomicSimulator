package com.terry.economicsimulator.models.input.expression;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class IDExpression {
  String eq;
  String ne;
  List<String> in;
  List<String> notIn;
}
