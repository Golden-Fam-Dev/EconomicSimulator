package com.terry.economicsimulator.db.repo.expression;

import com.terry.economicsimulator.models.ResourceType;
import com.terry.economicsimulator.models.input.expression.ResourceTypeExpression;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.TableField;

import java.util.List;
import java.util.Objects;

public final class ResourceTypeExpressionHandler {
  private ResourceTypeExpressionHandler() {
  }

  public static <R extends Record> Condition handleExpression(ResourceTypeExpression expression, Condition condition, TableField<R, Short> tableField) {
    if (expression == null) {
      return condition;
    }
    if (expression.getEq() != null) {
      condition = condition.and(tableField.eq(expression.getEq().getResourceTypeId()));
    }
    if (expression.getNe() != null) {
      condition = condition.and(tableField.ne(expression.getNe().getResourceTypeId()));
    }
    if (expression.getIn() != null) {
      List<Short> locationTypeCodes = getResourceTypeCodes(expression.getIn());
      condition = condition.and(tableField.in(locationTypeCodes));
    }

    if (expression.getNotIn() != null) {
      List<Short> locationTypeCodes = getResourceTypeCodes(expression.getNotIn());
      condition = condition.and(tableField.notIn(locationTypeCodes));
    }

    return condition;
  }

  private static List<Short> getResourceTypeCodes(List<ResourceType> locationTypes) {
    return locationTypes.stream()
        .filter(Objects::nonNull)
        .map(ResourceType::getResourceTypeId)
        .toList();
  }
}
