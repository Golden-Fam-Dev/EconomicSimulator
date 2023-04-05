package com.terry.economicsimulator.db.repo.expression;

import com.terry.economicsimulator.models.input.expression.IntExpression;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.TableField;

public final class IntExpressionHandler {
  private IntExpressionHandler() {
  }

  public static <R extends Record> Condition handleExpression(IntExpression expression, Condition condition, TableField<R, Integer> tableField) {
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
    if (expression.getIn() != null) {
      condition = condition.and(tableField.in(expression.getIn()));
    }
    if (expression.getNotIn() != null) {
      condition = condition.and(tableField.notIn(expression.getNotIn()));
    }
    return condition;
  }
}