package com.terry.economicsimulator.db.repo.expression;

import com.terry.economicsimulator.models.input.expression.StringExpression;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.TableField;

public final class StringExpressionHandler {
  private StringExpressionHandler() {
  }

  public static <R extends Record> Condition handleExpression(StringExpression expression, Condition condition, TableField<R, String> tableField) {
    if (expression == null) {
      return condition;
    }
    if (expression.getEq() != null) {
      condition = condition.and(tableField.eq(expression.getEq()));
    }
    if (expression.getNe() != null) {
      condition = condition.and(tableField.ne(expression.getNe()));
    }
    if (expression.getContains() != null) {
      condition = condition.and(tableField.contains(expression.getContains()));
    }

    return condition;
  }
}
