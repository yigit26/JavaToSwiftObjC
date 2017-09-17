package com.yigitcanture.convertor.exception;

public class FileCouldNotCreatedException extends ExtException {

	private static final long serialVersionUID = 1L;

	public FileCouldNotCreatedException(Throwable e) {
		super("File could not created", e);
	}
}
