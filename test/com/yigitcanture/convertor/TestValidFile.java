package com.yigitcanture.convertor;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

import com.yigitcanture.convertor.IConvertor;
import com.yigitcanture.convertor.exception.FileCouldNotCreatedException;
import com.yigitcanture.convertor.exception.FileCouldNotReadException;

public class TestValidFile extends ExtTest {

	@Test
	public void testSameContent() throws FileCouldNotCreatedException, FileCouldNotReadException, IOException {
		IConvertor generator = createValidObjCConvertor();
		generator.generate(null);
		assertTrue(sameContent(new File("./dest/SampleObject.h").toPath(), new File("./data/SampleObject.h").toPath()));
		assertTrue(sameContent(new File("./dest/SampleObject.m").toPath(), new File("./data/SampleObject.m").toPath()));
		
		assertTrue(sameContent(new File("./dest/SampleObject2.h").toPath(), new File("./data/SampleObject2.h").toPath()));
		assertTrue(sameContent(new File("./dest/SampleObject2.m").toPath(), new File("./data/SampleObject2.m").toPath()));
		
		assertTrue(sameContent(new File("./dest/EmptyObject.h").toPath(), new File("./data/EmptyObject.h").toPath()));
		assertTrue(sameContent(new File("./dest/EmptyObject.m").toPath(), new File("./data/EmptyObject.m").toPath()));
	}
	
	boolean sameContent(Path file1, Path file2) throws IOException {
	    final long size = Files.size(file1);
	    if (size != Files.size(file2))
	        return false;
	    try (InputStream is1 = Files.newInputStream(file1);
	         InputStream is2 = Files.newInputStream(file2)) {
	        int data;
	        while ((data = is1.read()) != -1)
	            if (data != is2.read())
	                return false;
	    }
	    return true;
	}

}
