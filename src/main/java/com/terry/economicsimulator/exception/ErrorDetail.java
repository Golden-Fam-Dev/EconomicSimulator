package com.terry.economicsimulator.exception;

import graphql.ErrorClassification;
import graphql.GraphQLError;

public interface ErrorDetail  extends ErrorClassification {
  ErrorType getErrorType();

  enum Common implements ErrorDetail {
    DEADLINE_EXCEEDED(ErrorType.UNAVAILABLE),
    ENHANCE_YOUR_CALM(ErrorType.UNAVAILABLE),
    FIELD_NOT_FOUND(ErrorType.BAD_REQUEST),
    INVALID_ARGUMENT(ErrorType.BAD_REQUEST),
    INVALID_CURSOR(ErrorType.NOT_FOUND),
    MISSING_RESOURCE(ErrorType.FAILED_PRECONDITION),
    CONFLICT(ErrorType.FAILED_PRECONDITION),
    SERVICE_ERROR(ErrorType.UNAVAILABLE),
    THROTTLED_CONCURRENCY(ErrorType.UNAVAILABLE),
    THROTTLED_CPU(ErrorType.UNAVAILABLE),
    UNIMPLEMENTED(ErrorType.BAD_REQUEST);

    private final ErrorType errorType;

    Common(ErrorType errorType) {
      this.errorType = errorType;
    }

    @Override
    public Object toSpecification(GraphQLError error) {
      return this.errorType + "." + this;
    }

    public ErrorType getErrorType() {
      return this.errorType;
    }
  }
}
