package com.insieme.common.domain.rest;

import com.insieme.common.domain.dto.RestEntity;
import com.insieme.common.domain.dto.InsiemeExceptionEntity;

/**
 * This class wraps a dto returned and signals whether the result of a REsT operation 
 * was successful or not. If it wasn't I provide the error, otherwise I provide the actual 
 * {@link RestEntity} result.
 *
 * @param <T> the generic type which extends {@link RestEntity}.
 */
public class RestResult<T extends RestEntity> {

	private final Boolean isSuccessful;
	
	private final InsiemeExceptionEntity insiemeExceptionDto;
	
	private final T restResultDto;

	/**
	 * Default Constructor.
	 *
	 * @param isSuccessful the is successful
	 * @param restResultDto the rest result dto
	 * @param insiemeExceptionDto the insieme exception dto
	 */
	private RestResult(final Boolean isSuccessful, 
					   final T restResultDto, 
					   final InsiemeExceptionEntity insiemeExceptionDto) {
		this.isSuccessful = isSuccessful;
		this.restResultDto = restResultDto;
		this.insiemeExceptionDto = insiemeExceptionDto;
	}
	
	public Boolean isSuccessfull() {
		return this.isSuccessful == Boolean.TRUE;
	}
	
	public Boolean isFailure() {
		return this.isSuccessful == Boolean.FALSE;
	}
	
	public InsiemeExceptionEntity getError() {
		return this.insiemeExceptionDto;
	}
	
	public T getRestResult() {
		return this.restResultDto;
	}
	
	/**
	 * Inner builder class used to create a {@link RestResult}.
	 * 
	 * Look at the builder pattern for more information on why I chose this 
	 * approach.
	 *
	 * @param <T> the generic type
	 */
	public static final class RestResultBuilder<T extends RestEntity> {
		
		private Boolean isSuccessful;
		
		private InsiemeExceptionEntity insiemeExceptionDto;
		
		private T restResultDto;

		public RestResultBuilder<T> setRestResult(T restResponseDto) {
			this.restResultDto = restResponseDto;
			this.isSuccessful = Boolean.TRUE;
			return this;
		}
		
		public RestResultBuilder<T> setError(InsiemeExceptionEntity insiemeExceptionDto) {
			this.insiemeExceptionDto = insiemeExceptionDto;
			this.isSuccessful = Boolean.FALSE;
			return this;
		}
		
		/**
		 * Builds the {@link RestResult} and ensures paramaters are used to build are valid.
		 *
		 * @return the rest result
		 */
		public RestResult<T> build() {
			assert this.isSuccessful != null : "rest result must be either successful or not.";
			if (this.isSuccessful) {
				assert this.insiemeExceptionDto == null : "no exception should have occured if rest result succeeded";
				assert this.restResultDto != null : "requests that succeed should have resultDtos.";
			} else {
				assert this.insiemeExceptionDto != null : "exception should have occured rest result failed";
				assert this.restResultDto == null : "requests that do not succeed should not have resultDtos.";
			}
			return new RestResult<T>(this.isSuccessful, this.restResultDto, this.insiemeExceptionDto);
		}
	}
}
