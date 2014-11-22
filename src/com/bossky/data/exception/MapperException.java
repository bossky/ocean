package com.bossky.data.exception;

/**
 * 映射异常
 * 
 * @author bo
 *
 */
public class MapperException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MapperException(String message) {
		super(message);
	}

	public MapperException(String message, Throwable cause) {
		super(message, cause);
	}
}
