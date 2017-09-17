package com.yigitcanture.convertor;

import java.lang.reflect.Constructor;

import org.junit.Test;

import com.yigitcanture.convertor.util.CommentUtils;
import com.yigitcanture.convertor.util.FileUtils;
import com.yigitcanture.convertor.util.LoggerUtils;
import com.yigitcanture.convertor.util.StringConstants;

public class TestPrivateConstructors {

	@Test
	public void testStringUtilsPrivateConstructor() {
		Constructor<StringConstants> cnt;
		try {
			cnt = StringConstants.class.getDeclaredConstructor();
			cnt.setAccessible(true);

			cnt.newInstance();
		} catch (Exception e) {
			LoggerUtils.getLoggerInstance().error(e.getMessage(), e);
		}
	}
	
	@Test
	public void testCommentUtilsPrivateConstructor() {
		Constructor<CommentUtils> cnt;
		try {
			cnt = CommentUtils.class.getDeclaredConstructor();
			cnt.setAccessible(true);

			cnt.newInstance();
		} catch (Exception e) {
			LoggerUtils.getLoggerInstance().error(e.getMessage(), e);
		}
	}
	
	@Test
	public void testFileUtilsPrivateConstructor() {
		Constructor<FileUtils> cnt;
		try {
			cnt = FileUtils.class.getDeclaredConstructor();
			cnt.setAccessible(true);

			cnt.newInstance();
		} catch (Exception e) {
			LoggerUtils.getLoggerInstance().error(e.getMessage(), e);
		}
	}

	@Test
	public void testLoggerUtilsPrivateConstructor() {
		Constructor<LoggerUtils> cnt;
		try {
			cnt = LoggerUtils.class.getDeclaredConstructor();
			cnt.setAccessible(true);

			cnt.newInstance();
		} catch (Exception e) {
			LoggerUtils.getLoggerInstance().error(e.getMessage(), e);
		}
	}

}
