package com.yigitcanture.convertor;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.yigitcanture.convertor.IConvertor;
import com.yigitcanture.convertor.exception.FileCouldNotCreatedException;
import com.yigitcanture.convertor.exception.FileCouldNotReadException;
import com.yigitcanture.convertor.exception.PathOrNameNotFoundException;
import com.yigitcanture.convertor.util.LoggerUtils;

public class TestSwiftConvertor extends ExtTest {

	@Test(expected = PathOrNameNotFoundException.class)
	public void testWithoutPath() {
		createNonValidSwiftConvertor();
	}
	
	@Test
	public void testCreateGenerator() {
		IConvertor generator = createValidSwiftConvertor();
		assertNotNull(generator);
	}
	
	@Test
	public void testWitoutExist() {
		IConvertor generator = createValidSwiftConvertor();
		try {
			File[] list = new File("./dest").listFiles();
			for (File file : list) {
				file.delete();
			}
			generator.generate(null);
			assertTrue(true);
		} catch (FileCouldNotCreatedException | FileCouldNotReadException e) {
			LoggerUtils.getLoggerInstance().error(e.getMessage(), e);
		}
	}
	
	@Test
	public void testWithExist() {
		IConvertor generator = createValidSwiftConvertor();
		try {
			generator.generate(null);
			generator.generate(null);
			assertTrue(true);
		} catch (FileCouldNotCreatedException | FileCouldNotReadException e) {
			LoggerUtils.getLoggerInstance().error(e.getMessage(), e);
		}
	}

	@Test(expected = FileCouldNotCreatedException.class)
	public void testCouldNotCreate() throws FileCouldNotCreatedException {
		IConvertor generator = createNonAccessibleSwiftConvertor();
		try {
			generator.generate(null);
		} catch (FileCouldNotReadException e) {
			LoggerUtils.getLoggerInstance().error(e.getMessage(),e);
		}
		fail("Not Excepted");

	}

}