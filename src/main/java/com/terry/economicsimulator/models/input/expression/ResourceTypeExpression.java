package com.terry.economicsimulator.models.input.expression;

import com.terry.economicsimulator.models.ResourceType;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class ResourceTypeExpression {
  ResourceType eq;
  ResourceType ne;
  List<ResourceType> in;
  List<ResourceType> notIn;
}
