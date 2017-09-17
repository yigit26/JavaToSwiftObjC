package com.yigitcanture.convertor;

import java.io.File;

import com.yigitcanture.convertor.exception.FileCouldNotCreatedException;
import com.yigitcanture.convertor.exception.FileCouldNotReadException;
import com.yigitcanture.convertor.util.LoggerUtils;

public interface IConvertor {
	
	void generate(IConvertorCallback callback) throws FileCouldNotCreatedException, FileCouldNotReadException;

	/***
	 * 
	 * @param filename Indicates name of the class.
	 * @param file Indicates java source file.
	 * @throws FileCouldNotCreatedException {@link FileCouldNotCreatedException}
	 * @throws FileCouldNotReadException {@link FileCouldNotReadException}
	 */
	void generateFiles(String filename, File file)
			throws FileCouldNotCreatedException, FileCouldNotReadException;

	/***
	 * This method is used to find files on the given path.
	 * @param currentPath Indicates search path
	 * @throws FileCouldNotCreatedException {@link FileCouldNotCreatedException}
	 * @throws FileCouldNotReadException {@link FileCouldNotReadException}
	 */
	default void findFiles(String currentPath) throws FileCouldNotCreatedException, FileCouldNotReadException {
		File folder = new File(currentPath);
		File[] listOfFiles = folder.listFiles();
		String filenameForGenerating = "";
		for (File fi : listOfFiles) {
			if (!fi.isDirectory()) {
				if (fi.getName().contains(".java")) {
					filenameForGenerating = fi.getName().replace(".java", "");
					LoggerUtils.getLoggerInstance().info(filenameForGenerating + " will generate...");
					generateFiles(filenameForGenerating, fi);
					LoggerUtils.getLoggerInstance().info(filenameForGenerating + " was generated.");
				} else {
					LoggerUtils.getLoggerInstance().info("File not in valid format.");
				}

			} else {
				findFiles(fi.getAbsolutePath());
			}
		}
	}
}
