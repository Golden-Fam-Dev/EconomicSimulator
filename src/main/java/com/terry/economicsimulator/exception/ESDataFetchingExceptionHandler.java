package com.terry.economicsimulator.exception;

import graphql.GraphQLError;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import graphql.execution.ResultPath;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Component
public class ESDataFetchingExceptionHandler implements DataFetcherExceptionHandler {
	private final DataFetcherExceptionHandler defaultHandler = new DefaultDataFetcherExceptionHandler();

	@Override
	public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(DataFetcherExceptionHandlerParameters handlerParameters) {
		Throwable exception = handlerParameters.getException();
		Throwable underlyingException = exception;
		if (exception instanceof CompletionException) {
			underlyingException = exception.getCause();
		}

		if (underlyingException instanceof EntityNotFoundException) {
			DataFetcherExceptionHandlerResult exceptionHandlerResult = createExceptionHandlerResult(TypedGraphQLError.newNotFoundBuilder(), underlyingException.getMessage(), handlerParameters.getPath());
			return CompletableFuture.completedFuture(exceptionHandlerResult);
		} else if (underlyingException instanceof InputValidationException) {
			DataFetcherExceptionHandlerResult exceptionHandlerResult = createExceptionHandlerResult(TypedGraphQLError.newBadRequestBuilder(), underlyingException.getMessage(), handlerParameters.getPath());
			return CompletableFuture.completedFuture(exceptionHandlerResult);
		} else {
			DataFetcherExceptionHandlerParameters paramsWithUnderlyingException = DataFetcherExceptionHandlerParameters.newExceptionParameters()
					.dataFetchingEnvironment(handlerParameters.getDataFetchingEnvironment())
					.exception(underlyingException)
					.build();
			return defaultHandler.handleException(paramsWithUnderlyingException);
		}
	}

	private DataFetcherExceptionHandlerResult createExceptionHandlerResult(TypedGraphQLError.Builder tgBuilder, String message, ResultPath path) {
		GraphQLError graphqlError = tgBuilder
				.message(message)
				.path(path)
				.build();
		return DataFetcherExceptionHandlerResult.newResult()
				.error(graphqlError)
				.build();
	}
}
