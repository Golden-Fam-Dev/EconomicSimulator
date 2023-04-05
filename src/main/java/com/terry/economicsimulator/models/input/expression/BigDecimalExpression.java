package com.terry.economicsimulator.models.input.expression;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
@Jacksonized
public class BigDecimalExpression {
    BigDecimal eq;
    BigDecimal ne;
    BigDecimal lt;
    BigDecimal lte;
    BigDecimal gt;
    BigDecimal gte;
    List<BigDecimal> in;
    List<BigDecimal> notIn;
}
