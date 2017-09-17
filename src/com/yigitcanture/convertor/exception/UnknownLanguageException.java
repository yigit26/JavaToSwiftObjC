package com.yigitcanture.convertor.exception;

public class UnknownLanguageException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public UnknownLanguageException() {
		super("Language could not find.");
	}
}
