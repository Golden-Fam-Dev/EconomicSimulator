package com.terry.economicsimulator.db.repo.handler;

import com.terry.economicsimulator.models.input.filter.Filter;
import org.jooq.Condition;

public interface IFilterHandler<F extends Filter> {
  /**
   * Returns the class of the Filter which this FilterHandler supports
   *
   * @return A filter implementation's class
   */
  Class<? extends Filter> supportedFilter();

  /**
   * Handles the given filter, returning a Condition
   * which has the relevant filter fields supported
   *
   * @param filter Filter to handle
   * @return Condition which has
   */
  Condition handleFilter(F filter);

  default Condition handleFilterAnd(Condition condition, F filter) {
    return condition.and(handleFilter(filter));
  }
}
