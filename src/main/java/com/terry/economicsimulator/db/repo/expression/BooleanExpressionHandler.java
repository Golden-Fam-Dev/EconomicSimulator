package com.terry.economicsimulator.db.repo.expression;

import com.terry.economicsimulator.models.input.expression.BooleanExpression;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.TableField;

public final class BooleanExpressionHandler {
  private BooleanExpressionHandler() {
  }

  public static <R extends Record> Condition handleExpression(BooleanExpression expression, Condition condition, TableField<R, Boolean> tableField) {
    if (expression == null) {
      return condition;
    }
    if (expression.getEq() != null) {
      condition = condition.and(tableField.eq(expression.getEq()));
    }
    if (expression.getNe() != null) {
      condition = condition.and(tableField.ne(expression.getNe()));
    }
    return condition;
  }
}
