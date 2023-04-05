package com.terry.economicsimulator.db.repo.handler;

import com.terry.economicsimulator.models.input.filter.Filter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Factory which manages and serves up any available IFilterHandler implementations.
 *
 * If used through Spring, all implementations of IFilterHandler will be automatically
 * injected into the factory and accessible through the getFilterHandler function.
 */
@Component
public class FilterHandlerFactory {
  private final Map<Class<? extends Filter>, IFilterHandler<? extends Filter>> filterHandlerMap;

  public FilterHandlerFactory(List<IFilterHandler<? extends Filter>> filterHandlers) {
    if (filterHandlers == null || filterHandlers.isEmpty()) {
      throw new IllegalArgumentException("At least one filterHandler must be present");
    }
    filterHandlerMap = initFilterHandlerMap(filterHandlers);
  }

  public <F extends Filter> IFilterHandler<F> getFilterHandler(Filter filter) {
    @SuppressWarnings("unchecked")
    IFilterHandler<F> filterHandler = (IFilterHandler<F>) filterHandlerMap.get(filter.getClass());
    if (filterHandler == null) {
      throw new IllegalArgumentException("No filterHandler found for filter of type " + filter.getClass());
    }
    return filterHandler;
  }

  private Map<Class<? extends Filter>, IFilterHandler<? extends Filter>> initFilterHandlerMap(List<IFilterHandler<? extends Filter>> filterHandlers) {
    return filterHandlers.stream()
        .collect(Collectors.toMap(
            IFilterHandler::supportedFilter,
            filterHandler -> filterHandler
        ));
  }
}
