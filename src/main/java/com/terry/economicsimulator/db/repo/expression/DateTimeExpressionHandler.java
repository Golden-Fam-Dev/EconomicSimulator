package com.terry.economicsimulator.db.repo.expression;

import com.terry.economicsimulator.models.input.expression.DateTimeExpression;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.TableField;

import java.time.LocalDateTime;

public final class DateTimeExpressionHandler {
  private DateTimeExpressionHandler() {
  }

  public static <R extends Record> Condition handleExpression(DateTimeExpression expression, Condition condition, TableField<R, LocalDateTime> tableField) {
    if (expression == null) {
      return condition;
    }
    if (expression.getEq() != null) {
      condition = condition.and(tableField.eq(expression.getEq().toLocalDateTime()));
    }
    if (expression.getNe() != null) {
      condition = condition.and(tableField.ne(expression.getNe().toLocalDateTime()));
    }
    if (expression.getLt() != null) {
      condition = condition.and(tableField.lt(expression.getLt().toLocalDateTime()));
    }
    if (expression.getLte() != null) {
      condition = condition.and(tableField.le(expression.getLte().toLocalDateTime()));
    }
    if (expression.getGt() != null) {
      condition = condition.and(tableField.gt(expression.getGt().toLocalDateTime()));
    }
    if (expression.getGte() != null) {
      condition = condition.and(tableField.ge(expression.getGte().toLocalDateTime()));
    }
    return condition;
  }
}
