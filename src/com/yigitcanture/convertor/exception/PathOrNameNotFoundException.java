package com.yigitcanture.convertor.exception;

public class PathOrNameNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public PathOrNameNotFoundException() {
		super("File pats or company name could not find.");
	}
}
