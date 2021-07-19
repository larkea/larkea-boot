package com.larkea.boot.core.data;

/**
 * Convert to DTO
 *
 * @param <T> dto type
 */
public interface ToData<T> {

	T toData();
}
