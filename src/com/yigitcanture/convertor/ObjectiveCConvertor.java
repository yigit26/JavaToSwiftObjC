package com.yigitcanture.convertor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yigitcanture.convertor.exception.FileCouldNotCreatedException;
import com.yigitcanture.convertor.exception.FileCouldNotReadException;
import com.yigitcanture.convertor.model.Convertor;
import com.yigitcanture.convertor.util.CommentUtils;
import com.yigitcanture.convertor.util.FileUtils;
import com.yigitcanture.convertor.util.LoggerUtils;
import com.yigitcanture.convertor.util.StringConstants;
import com.yigitcanture.convertor.util.VariablesUtils;

class ObjectiveCConvertor extends BaseConvertor implements IConvertor {
	private List<String> externalImportList;

	ObjectiveCConvertor(Convertor convertor) {
		super(convertor);
	}

	@Override
	public void generate(IConvertorCallback callback) throws FileCouldNotCreatedException, FileCouldNotReadException {
		this.callback = callback;
		findFiles(this.convertor.getSourcePath());
	}

	@Override
	public void generateFiles(String filename, File file)
			throws FileCouldNotCreatedException, FileCouldNotReadException {
		generateM(filename);
		generateH(filename, file);
		if (callback != null) {
			callback.notify(filename + ".h / " + filename + ".m were generated.");
		}
	}

	/*
	 * GENERATE M FILE START
	 */
	/***
	 * This method is used to generating .m files.
	 * 
	 * @param filename
	 *            .java file's name.
	 * @throws FileCouldNotCreatedException
	 */
	private void generateM(String filename) throws FileCouldNotCreatedException {
		File file = new File(this.convertor.getDestinationPath() + File.separator + filename + ".m");
		try {
			if (file.createNewFile()) {
				LoggerUtils.getLoggerInstance().info(filename + ".m file created.");
			} else {
				LoggerUtils.getLoggerInstance().info(filename + ".m file is exist or could not created.");
			}
		} catch (IOException e) {
			throw new FileCouldNotCreatedException(e);
		}
		try (FileWriter writer = new FileWriter(file)) {
			StringBuilder sb = new StringBuilder();
			sb.append(CommentUtils.createComment(filename + ".m", convertor.getCompanyName(),
					convertor.getProjectName()));
			sb.append(generateImport(filename));
			writer.write(sb.toString());
			writer.close();
		} catch (IOException e) {
			LoggerUtils.getLoggerInstance().error(e.getMessage(), e);
		}
	}

	/***
	 * This method is used to create import statement
	 * 
	 * @param filename
	 * @return
	 */
	private String generateImport(String filename) {
		StringBuilder sb = new StringBuilder();
		sb.append("#import \"" + filename + ".h\" \n\n");
		sb.append("@implementation " + filename + "\n\n");
		sb.append("\n@end");
		return sb.toString();
	}
	// ************ GENERATE M FILE FINISH ************

	/*
	 * GENERATE H FILE START
	 */
	/***
	 * This method is used to generate .h files.
	 * 
	 * @param filename
	 *            .java file's name
	 * @param sourceFile
	 *            .java file
	 * @throws FileCouldNotCreatedException
	 * @throws FileCouldNotReadException
	 */
	private void generateH(String filename, File sourceFile)
			throws FileCouldNotCreatedException, FileCouldNotReadException {
		try {
			String textJavaFile = FileUtils.readFile(sourceFile);
			String props = findProperties(textJavaFile);
			for (HashMap.Entry<String, String> val : VariablesUtils.getInstance().getObjCVariableMap().entrySet()) {
				props = props.replaceAll(val.getKey(), val.getValue());
			}
			props = findSpecialProperties(props);
			File file = new File(this.convertor.getDestinationPath() + File.separator + filename + ".h");
			if (file.createNewFile()) {
				LoggerUtils.getLoggerInstance().info(filename + ".h file created.");
			} else {
				LoggerUtils.getLoggerInstance().info(filename + ".h file is exist or could not created.");
			}
			writeHFile(file, props, filename);
		} catch (IOException e) {
			LoggerUtils.getLoggerInstance().error(e.getMessage(), e);
			throw new FileCouldNotCreatedException(e);
		}
	}

	/***
	 * This method is used to find Java types. Like String,Date,HashMap etc.
	 * 
	 * @param textJavaFile
	 * @return
	 */
	private String findProperties(String textJavaFile) {
		Pattern pat = Pattern.compile(StringConstants.PRIVATE_PATTERN);
		Matcher match = pat.matcher(textJavaFile);
		StringBuilder propsBuilder = new StringBuilder();
		while (match.find()) {
			String s = match.group();
			s = s.trim();
			if (s.charAt(s.length() - 1) != ';') {
				s += ";";
			}
			propsBuilder.append(s);
			propsBuilder.append("\n");
		}
		return propsBuilder.toString();
	}

	/***
	 * This method is used to find special classes which are created by users.
	 * 
	 * @param props
	 *            Indicates
	 * @return {@link String} returns properties which were found in source file
	 */
	private String findSpecialProperties(String props) {
		Pattern pattern = Pattern.compile(StringConstants.PRIVATE_PATTERN_2);
		Matcher match2 = pattern.matcher(props);
		externalImportList = new ArrayList<>();
		while (match2.find()) {
			String s = match2.group().replace("private ", "");
			if (!externalImportList.contains(s))
				externalImportList.add(s);
		}
		String changedProps = props.replace("private", StringConstants.STRONG);
		for (String s : externalImportList) {
			changedProps = changedProps.replace(s, s + "*");
		}
		return changedProps;
	}

	/**
	 * This method is used to write .h file.
	 * 
	 * @param file
	 *            Indicates destination file.
	 * @param props
	 *            Indicates destination file name.
	 * @param filename
	 *            Indicates destination file name.
	 */
	private void writeHFile(File file, String props, String filename) {
		try (FileWriter writer = new FileWriter(file)) {
			StringBuilder sb = new StringBuilder();
			sb.append(CommentUtils.createComment(filename + ".h", convertor.getCompanyName(),
					convertor.getProjectName()));
			sb.append(generateImports());
			String properties = createObjCProps(props);
			sb.append("\n\n@interface " + filename + " \n");
			sb.append(properties);
			sb.append("\n\n@end");
			writer.write(sb.toString());
			writer.close();
		} catch (Exception e) {
			LoggerUtils.getLoggerInstance().error(e.getMessage(), e);
		}
	}

	/***
	 * This method is used to create import statements.
	 * 
	 * @return {@link String} returns import statements.
	 */
	private String generateImports() {
		StringBuilder sb = new StringBuilder();
		if (externalImportList != null && !externalImportList.isEmpty()) {
			for (String s : externalImportList) {
				sb.append("#import \"" + s + ".h\"\n");
			}
		}

		return sb.toString();
	}

	/***
	 * This method is used to create ObjC properties
	 * 
	 * @param Indicates
	 *            properties which are found from source file
	 * @return {@link String} returns Objective C properties.
	 */
	private String createObjCProps(String props) {
		String[] strProps = props.split("\\n");
		StringBuilder propsBuilder = new StringBuilder();
		for (String str : strProps) {
			propsBuilder.append(str);
			propsBuilder.append("\n");
		}
		return propsBuilder.toString();
	}
	// ************ GENERATE H FILE END ************

}
