package com.terry.economicsimulator.db.repo.expression;

import com.terry.economicsimulator.models.input.expression.IDExpression;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.TableField;

import java.util.List;

public final class ShortIDExpressionHandler {
  private ShortIDExpressionHandler() {
  }

  public static <R extends Record> Condition handleExpression(IDExpression expression, Condition condition, TableField<R, Short> tableField) {
    if (expression == null) {
      return condition;
    }
    if (expression.getEq() != null) {
      Short intIdEq = Short.parseShort(expression.getEq());
      condition = condition.and(tableField.eq(intIdEq));
    }
    if (expression.getNe() != null) {
      Short intIdNe = Short.parseShort(expression.getNe());
      condition = condition.and(tableField.ne(intIdNe));
    }
    if (expression.getIn() != null) {
      List<Short> intIdListIn = getShortList(expression.getIn());
      condition = condition.and(tableField.in(intIdListIn));
    }
    if (expression.getNotIn() != null) {
      List<Short> intIdListNotIn = getShortList(expression.getNotIn());
      condition = condition.and(tableField.notIn(intIdListNotIn));
    }
    return condition;
  }

  private static List<Short> getShortList(List<String> stringList) {
    return stringList.stream()
        .map(Short::parseShort)
        .toList();
  }
}
