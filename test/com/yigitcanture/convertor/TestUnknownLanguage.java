package com.yigitcanture.convertor;

import org.junit.Test;

import com.yigitcanture.convertor.exception.UnknownLanguageException;

public class TestUnknownLanguage extends ExtTest {

	@Test(expected = UnknownLanguageException.class)
	public void testUnknownLanguage() {
		createValidConvertorWithoutLanguage();
	}
}
