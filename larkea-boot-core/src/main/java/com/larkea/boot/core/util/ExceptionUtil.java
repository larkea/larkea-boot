package com.larkea.boot.core.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class ExceptionUtil extends ExceptionUtils {

	private ExceptionUtil() {
	}

	/**
	 * Change CheckedException to UncheckedException.
	 *
	 * @param throwable Throwable
	 * @return RuntimeException
	 */
	public static RuntimeException unchecked(Throwable throwable) {
		if (throwable instanceof Error) {
			throw (Error) throwable;
		}
		else if (throwable instanceof IllegalAccessException ||
				throwable instanceof IllegalArgumentException ||
				throwable instanceof NoSuchMethodException) {
			return new IllegalArgumentException(throwable);
		}
		else if (throwable instanceof InvocationTargetException) {
			return runtime(((InvocationTargetException) throwable).getTargetException());
		}
		else if (throwable instanceof RuntimeException) {
			return (RuntimeException) throwable;
		}
		else if (throwable instanceof InterruptedException) {
			Thread.currentThread().interrupt();
		}
		return runtime(throwable);
	}

	/**
	 * Throw a runtime exception directly.
	 *
	 * @param throwable Throwable
	 * @param <T>       Generic Type
	 * @return Throwable
	 * @throws <T> Throwable
	 */
	@SuppressWarnings("unchecked")
	private static <T extends Throwable> T runtime(Throwable throwable) throws T {
		throw (T) throwable;
	}

	/**
	 * unwraps InvocationTargetException.
	 *
	 * @param throwable Throwable
	 * @return Throwable
	 */
	public static Throwable unwraps(Throwable throwable) {
		Throwable unwrapped = throwable;
		while (true) {
			if (unwrapped instanceof InvocationTargetException) {
				unwrapped = ((InvocationTargetException) unwrapped).getTargetException();
			}
			else if (unwrapped instanceof UndeclaredThrowableException) {
				unwrapped = ((UndeclaredThrowableException) unwrapped).getUndeclaredThrowable();
			}
			else {
				return unwrapped;
			}
		}
	}

	/**
	 * Make StackTrace to String.
	 *
	 * @param throwable Throwable
	 * @return String
	 */
	public static String getStackTraceAsString(Throwable throwable) {
		return getStackTrace(throwable);
	}
}
