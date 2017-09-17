package com.yigitcanture.convertor;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;

import com.yigitcanture.convertor.IConvertor;
import com.yigitcanture.convertor.exception.FileCouldNotCreatedException;
import com.yigitcanture.convertor.exception.FileCouldNotReadException;
import com.yigitcanture.convertor.exception.PathOrNameNotFoundException;
import com.yigitcanture.convertor.util.LoggerUtils;

public class TestObjCConvertor extends ExtTest {

	@Test(expected = PathOrNameNotFoundException.class)
	public void testWithoutPath() {
		createNonValidObjCConvertor();
	}

	@Test
	public void testCreateGenerator() {
		IConvertor generator = createValidObjCConvertor();
		assertNotNull(generator);
	}

	@Test
	public void testWithoutDestFiles() {
		IConvertor generator = createValidObjCConvertor();
		try {
			File[] list = new File("./dest").listFiles();
			if (list != null && list.length > 0) {
				for (File file : list) {
					file.delete();
				}
			}
			generator.generate(null);
			assertTrue(true);
		} catch (FileCouldNotCreatedException | FileCouldNotReadException e) {
			LoggerUtils.getLoggerInstance().error(e.getMessage(), e);
		}
	}

	@Test
	public void testWithExist() {
		IConvertor generator = createValidObjCConvertor();
		try {
			generator.generate(null);
			generator.generate(filename -> LoggerUtils.getLoggerInstance().info(filename));
			assertTrue(true);
		} catch (FileCouldNotCreatedException | FileCouldNotReadException e) {
			LoggerUtils.getLoggerInstance().error(e.getMessage(), e);
		}
	}

	@Test(expected = FileCouldNotCreatedException.class)
	public void testCouldNotCreate() throws FileCouldNotCreatedException {
		IConvertor generator = createNonAccessibleObjCConvertor();
		try {
			generator.generate(null);
		} catch (FileCouldNotReadException e) {
			LoggerUtils.getLoggerInstance().error(e.getMessage(), e);
		}
		fail("Not Excepted");

	}

}
