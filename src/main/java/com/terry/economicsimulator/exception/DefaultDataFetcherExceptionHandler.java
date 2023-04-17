package com.terry.economicsimulator.exception;

import com.terry.economicsimulator.exception.TypedGraphQLError.Builder;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.ClassUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class DefaultDataFetcherExceptionHandler implements DataFetcherExceptionHandler {
  private final Logger logger = LoggerFactory.getLogger(DefaultDataFetcherExceptionHandler.class);

  @Override
  public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(DataFetcherExceptionHandlerParameters handlerParameters) {
    return CompletableFuture.completedFuture(doHandleException(handlerParameters));
  }

  private DataFetcherExceptionHandlerResult doHandleException(DataFetcherExceptionHandlerParameters handlerParameters) {
    Throwable exception = unwrapCompletionException(handlerParameters.getException());
    logger.error("Exception while executing data fetcher for " +
            handlerParameters.getPath() +
            ": " + exception.getMessage(),
        exception);

    Builder graphqlError;
    if (springSecurityAvailable()) { // && isSpringSecurityAccessException(exception)) {
      graphqlError = TypedGraphQLError.newPermissionDeniedBuilder();
    } else {
      graphqlError = TypedGraphQLError.newInternalErrorBuilder();
    }

    return DataFetcherExceptionHandlerResult.newResult()
        .error(graphqlError.message("%s: %s", exception.getClass().getName(), exception.getMessage())
            .path(handlerParameters.getPath())
            .build())
        .build();
  }

  private Throwable unwrapCompletionException(Throwable e) {
    if (e instanceof CompletionException eCE && eCE.getCause() != null) {
      return eCE.getCause();
    } else {
      return e;
    }
  }

  private @Lazy Boolean springSecurityAvailable() {
    return ClassUtils.isPresent("org.springframework.security.access.AccessDeniedException",
        DefaultDataFetcherExceptionHandler.class.getClassLoader());
  }

//  private Boolean isSpringSecurityAccessException(Throwable exception) {
//    try {
//      return exception instanceof org.springframework.security.access.AccessDeniedException;
//    } catch (Exception e) {
//      logger.trace("Unable to verify if {} is a Spring Security's AccessDeniedException.", exception, e);
//    }
//    return false;
//  }
}
