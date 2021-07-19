package com.larkea.boot.core.data;

/**
 * Convert from DTO
 *
 * @param <T> dto type
 */
public interface FromData<T> {

	/**
	 * Convert DTO to other
	 *
	 * @param data dto
	 * @param <R>  return type
	 * @return other object
	 */
	<R> R fromData(T data);
}
