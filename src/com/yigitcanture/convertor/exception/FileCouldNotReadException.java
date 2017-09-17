package com.yigitcanture.convertor.exception;

public class FileCouldNotReadException extends ExtException {
	private static final long serialVersionUID = 1L;

	public FileCouldNotReadException(Throwable e) {
		super("File could not read", e);
	}
}
