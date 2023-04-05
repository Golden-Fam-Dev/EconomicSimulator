package com.terry.economicsimulator.graphql.datafetchers;

import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Component
public class DataFetcherHelper {
	private final DataFetcherExceptionHandler esDataFetchingExceptionHandler;

	public DataFetcherHelper(DataFetcherExceptionHandler esDataFetchingExceptionHandler) {this.esDataFetchingExceptionHandler = esDataFetchingExceptionHandler;}

	public <T> CompletableFuture<DataFetcherResult<T>> handle(Supplier<CompletableFuture<T>> resultSupplier, DataFetchingEnvironment dfe) {
		return handle(resultSupplier, dfe, null);
	}

	/**
	 * Handles the CompletableFuture supplied by the given futureResultSupplier.  If any
	 * exception is thrown either within the completableFuture or outside it, the exception
	 * will be translated into an appropriate DataFetcherResult with the exception populated
	 * in the error field and given defaultData populated in the data field.
	 * If no exception occurs, the result is wrapped in a DataFetcherResult and returned.
	 *
	 * @param futureResultSupplier Supplied which returns a CompletableFuture with the result or exception
	 * @param dfe                  DataFetchingEnvironment
	 * @param defaultData          DefaultData to use within the DataFetcherResult in case of an error
	 * @param <T>                  result Type
	 * @return Completable future of the DataFetcherResult with the data and optionally error populated
	 */
	public <T> CompletableFuture<DataFetcherResult<T>> handle(Supplier<CompletableFuture<T>> futureResultSupplier, DataFetchingEnvironment dfe, T defaultData) {
		try {
			return futureResultSupplier.get()
					.handle((result, ex) -> {
						if (ex != null) {
							return handleError(dfe, ex, defaultData);
						} else {
							return DataFetcherResult.<T>newResult()
									.data(result != null ? result : defaultData)
									.build();
						}
					});
		} catch (Exception ex) {
			return CompletableFuture.completedFuture(handleError(dfe, ex, defaultData));
		}

	}

	public <T> DataFetcherResult<T> handleError(DataFetchingEnvironment dfe, Throwable t) {
		return handleError(dfe, t, null);
	}

	public <T> DataFetcherResult<T> handleError(DataFetchingEnvironment dfe, Throwable t, T defaultData) {
		DataFetcherExceptionHandlerParameters handlerParameters = DataFetcherExceptionHandlerParameters.newExceptionParameters()
				.dataFetchingEnvironment(dfe)
				.exception(t)
				.build();

		DataFetcherExceptionHandlerResult handledResult = esDataFetchingExceptionHandler.handleException(handlerParameters).join();

		return DataFetcherResult.<T>newResult().errors(handledResult.getErrors()).data(defaultData).build();
	}
}
