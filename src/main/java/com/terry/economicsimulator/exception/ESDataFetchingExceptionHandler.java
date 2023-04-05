package com.terry.economicsimulator.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import graphql.execution.SimpleDataFetcherExceptionHandler;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Component
public class ESDataFetchingExceptionHandler implements DataFetcherExceptionHandler {
	private final DataFetcherExceptionHandler defaultHandler = new SimpleDataFetcherExceptionHandler();

	@Override
	public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(DataFetcherExceptionHandlerParameters handlerParameters) {
		Throwable exception = handlerParameters.getException();
		Throwable underlyingException = exception;
		if (exception instanceof CompletionException) {
			underlyingException = exception.getCause();
		}
		DataFetchingEnvironment dfe = handlerParameters.getDataFetchingEnvironment();

		if (underlyingException instanceof InputValidationException) {
			GraphQLError graphqlError = GraphqlErrorBuilder.newError(dfe)
					.errorType(ErrorType.ValidationError)
					.message(underlyingException.getMessage())
					.path(handlerParameters.getPath())
					.build();
			return convertErrorHandlerErrorTypeField(DataFetcherExceptionHandlerResult.newResult()
					.error(graphqlError)
					.build());
		} else {
			DataFetcherExceptionHandlerParameters paramsWithUnderlyingException = DataFetcherExceptionHandlerParameters.newExceptionParameters()
					.dataFetchingEnvironment(handlerParameters.getDataFetchingEnvironment())
					.exception(underlyingException)
					.build();
			return defaultHandler.handleException(paramsWithUnderlyingException);
		}
	}

	public CompletableFuture<DataFetcherExceptionHandlerResult> convertErrorHandlerErrorTypeField(DataFetcherExceptionHandlerResult oldResult) {
		String errorType = "errorType";

		oldResult.getErrors()
				.forEach(error -> {
					if (error.getExtensions() != null && error.getExtensions().get(errorType) != null) {
						error.getExtensions().put("classification", error.getExtensions().get(errorType));
						error.getExtensions().remove(errorType);
					}
				});

		return CompletableFuture.completedFuture(oldResult);
	}
}
