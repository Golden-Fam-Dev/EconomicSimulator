package com.terry.economicsimulator.db.repo.expression;

import com.terry.economicsimulator.models.input.expression.TimeExpression;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.TableField;

import java.time.OffsetTime;

public final class TimeExpressionHandler {
  private TimeExpressionHandler() {
  }

  public static <R extends Record> Condition handleExpression(TimeExpression expression, Condition condition, TableField<R, OffsetTime> tableField) {
    if (expression == null) {
      return condition;
    }
    if (expression.getEq() != null) {
      condition = condition.and(tableField.eq(expression.getEq()));
    }
    if (expression.getNe() != null) {
      condition = condition.and(tableField.ne(expression.getNe()));
    }
    if (expression.getLt() != null) {
      condition = condition.and(tableField.lt(expression.getLt()));
    }
    if (expression.getLte() != null) {
      condition = condition.and(tableField.le(expression.getLte()));
    }
    if (expression.getGt() != null) {
      condition = condition.and(tableField.gt(expression.getGt()));
    }
    if (expression.getGte() != null) {
      condition = condition.and(tableField.ge(expression.getGte()));
    }
    return condition;
  }
}