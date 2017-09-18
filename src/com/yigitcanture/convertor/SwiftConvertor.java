package com.yigitcanture.convertor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
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

class SwiftConvertor extends BaseConvertor implements IConvertor {

	SwiftConvertor(Convertor convertor) {
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
		generateSwiftFile(filename, file);
		if (callback != null) {
			callback.notify(filename + ".swift was generated.");
		}
	}

	/***
	 * 
	 * @param filename
	 *            Indicates java class file name
	 * @param file
	 *            Indicates Java source file.
	 * @throws FileCouldNotReadException
	 * @throws FileCouldNotCreatedException
	 */
	private void generateSwiftFile(String filename, File file)
			throws FileCouldNotReadException, FileCouldNotCreatedException {
		try {
			mapVarType.clear();
			String textJavaFile = FileUtils.readFile(file);
			String props = findProperties(textJavaFile);
			findAndPutAllPropertiesToMap(props);
			File destinationFile = new File(convertor.getDestinationPath() + File.separator + filename + ".swift");
			if (destinationFile.createNewFile()) {
				LoggerUtils.getLoggerInstance().info(filename + ".swift file created.");
			} else {
				LoggerUtils.getLoggerInstance().info(filename + ".swift file is exist or could not created.");
			}
			writeSwiftFile(destinationFile, filename);
		} catch (IOException e) {
			LoggerUtils.getLoggerInstance().error(e.getMessage(), e);
			throw new FileCouldNotCreatedException(e);
		}
	}


	/***
	 * This method is used to fill {@link HashMap}<{@link String},{@link String}>.
	 * key is variable name. value is type of variable.
	 * 
	 * @param props
	 */
	private void findAndPutAllPropertiesToMap(String props) {
		Pattern pat = Pattern.compile(StringConstants.PATTERN_CATCH_PROPERTY);
		Matcher matcher = pat.matcher(props);
		while (matcher.find()) {
			String type = VariablesUtils.getInstance().getTypeSwiftFromJava(matcher.group(1));
			String varName = matcher.group(2);
			mapVarType.put(varName, type);
		}
	}

	/**
	 * This method is used to create and write .swift file
	 * 
	 * @param file Indicates destination file.
	 * @param filename Indicates source class name and file name. 
	 */
	private void writeSwiftFile(File file, String filename) {
		try (FileWriter writer = new FileWriter(file)) {
			StringBuilder sb = new StringBuilder();
			sb.append(CommentUtils.createComment(filename + ".swift", convertor.getCompanyName(),
					convertor.getProjectName()));
			String properties = createSwiftProperties();
			sb.append("\nimport UIKit\n");
			sb.append("\n\nclass " + filename + " {\n");
			sb.append(properties);
			sb.append("\n");
			sb.append(generateInitMethod());
			sb.append("\n");
			sb.append(generatePublicMethods());
			sb.append("\n}");
			writer.write(sb.toString());
			writer.close();
		} catch (Exception e) {
			LoggerUtils.getLoggerInstance().error(e.getMessage(), e);
		}
	}

	/***
	 * This method is used to create swift properties according to map ({@link HashMap}<{@link String},{@link String}>)
	 * @return returns swift properties.
	 */
	private String createSwiftProperties() {
		StringBuilder sb = new StringBuilder();
		for (HashMap.Entry<String, String> val : mapVarType.entrySet()) {
			sb.append("\n\tprivate var ");
			sb.append("_" + val.getKey() + " : ");
			sb.append(val.getValue() + "!");
		}
		return sb.toString();
	}

	/**
	 * This method is used to create init method with properties.
	 * @return returns init method with body.
	 */
	private String generateInitMethod() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n\tinit(");
		String comma = "";
		for (HashMap.Entry<String, String> val : mapVarType.entrySet()) {
			sb.append(comma);
			sb.append(val.getKey());
			sb.append(" : ");
			sb.append(val.getValue());
			comma = ",";
		}
		sb.append(") { \n");
		for (String varName : mapVarType.keySet()) {
			sb.append("\t\t_");
			sb.append(varName);
			sb.append(" = ");
			sb.append(varName);
			sb.append("\n");
		}
		sb.append("\t}");
		return sb.toString();
	}

	/***
	 * This method is used to create public properties to reach properties.
	 * @return returns all public properties
	 */
	private String generatePublicMethods() {
		StringBuilder sb = new StringBuilder();
		for (HashMap.Entry<String, String> val : mapVarType.entrySet()) {
			sb.append("\n\tvar ");
			sb.append(val.getKey());
			sb.append(" : ");
			sb.append(val.getValue() + "? {\n");
			sb.append("\t\t");
			sb.append("return _");
			sb.append(val.getKey());
			sb.append("\n");
			sb.append("\t}\n");
		}

		return sb.toString();
	}

}
