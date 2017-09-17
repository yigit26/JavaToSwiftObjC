package com.yigitcanture.convertor.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.yigitcanture.convertor.exception.FileCouldNotReadException;

public class FileUtils {
	
	private FileUtils() {
		
	}
	
	/***
	 * 
	 * @param file source file to read
	 * @return {@link String} content of the source file
	 * @throws FileCouldNotReadException
	 */
	public static String readFile(File file) throws FileCouldNotReadException {
		try (FileReader fileReader = new FileReader(file); BufferedReader reader = new BufferedReader(fileReader)) {
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();
			while (line != null) {
				line = line.replace("static ", "");
				line = line.replace("final ", "").trim();
				sb.append(line);
				sb.append(System.lineSeparator());
				line = reader.readLine();
			}
			fileReader.close();
			reader.close();
			return sb.toString();
		} catch (IOException e) {
			LoggerUtils.getLoggerInstance().error(e.getMessage(), e);
			throw new FileCouldNotReadException(e);
		}
	}
}
