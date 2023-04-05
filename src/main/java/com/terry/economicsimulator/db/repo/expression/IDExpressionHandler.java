package com.terry.economicsimulator.db.repo.expression;

import com.terry.economicsimulator.models.input.expression.IDExpression;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.TableField;

import java.util.List;

public final class IDExpressionHandler {
  private IDExpressionHandler() {
  }

  public static <R extends Record> Condition handleExpression(IDExpression expression, Condition condition, TableField<R, Integer> tableField) {
    if (expression == null) {
      return condition;
    }
    if (expression.getEq() != null) {
      Integer intIdEq = Integer.parseInt(expression.getEq());
      condition = condition.and(tableField.eq(intIdEq));
    }
    if (expression.getNe() != null) {
      Integer intIdNe = Integer.parseInt(expression.getNe());
      condition = condition.and(tableField.ne(intIdNe));
    }
    if (expression.getIn() != null) {
      List<Integer> intIdListIn = getIntList(expression.getIn());
      condition = condition.and(tableField.in(intIdListIn));
    }
    if (expression.getNotIn() != null) {
      List<Integer> intIdListNotIn = getIntList(expression.getNotIn());
      condition = condition.and(tableField.notIn(intIdListNotIn));
    }
    return condition;
  }

  private static List<Integer> getIntList(List<String> stringList) {
    return stringList.stream()
            .map(Integer::parseInt)
            .toList();
  }
}
