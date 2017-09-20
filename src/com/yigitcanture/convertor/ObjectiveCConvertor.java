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
		setCallback(callback);
		findFiles(getConvertor().getSourcePath());
	}

	@Override
	public void generateFiles(String filename, File file)
			throws FileCouldNotCreatedException, FileCouldNotReadException {
		generateM(filename);
		generateH(filename, file);
		if (getCallback() != null) {
			getCallback().notify(filename + ".h / " + filename + ".m were generated.");
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
		File file = new File(getConvertor().getDestinationPath() + File.separator + filename + ".m");
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
			sb.append(CommentUtils.createComment(filename + ".m", getConvertor().getCompanyName(),
					getConvertor().getProjectName()));
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
			findAndPutAllPropertiesToMap(props);
			File file = new File(getConvertor().getDestinationPath() + File.separator + filename + ".h");
			if (file.createNewFile()) {
				LoggerUtils.getLoggerInstance().info(filename + ".h file created.");
			} else {
				LoggerUtils.getLoggerInstance().info(filename + ".h file is exist or could not created.");
			}
			writeHFile(file, filename);
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
		externalImportList = new ArrayList<>();
		getMapVarType().clear();
		Pattern pat = Pattern.compile(StringConstants.PATTERN_CATCH_PROPERTY);
		Matcher matcher = pat.matcher(props);
		while (matcher.find()) {
			String javaType = matcher.group(1);
			String type = VariablesUtils.getInstance().getTypeObjCFromJava(javaType);
			if (type.contains("*") && type.equals(javaType + "*")) {
				externalImportList.add(javaType);
			}
			String varName = matcher.group(2);
			getMapVarType().put(varName, type);
		}
	}

	/**
	 * This method is used to write .h file.
	 * 
	 * @param file
	 *            Indicates destination file.
	 * @param filename
	 *            Indicates destination file name.
	 */
	private void writeHFile(File file, String filename) {
		try (FileWriter writer = new FileWriter(file)) {
			StringBuilder sb = new StringBuilder();
			sb.append(CommentUtils.createComment(filename + ".h", getConvertor().getCompanyName(),
					getConvertor().getProjectName()));
			sb.append(generateImports());
			String properties = createObjCProps();
			sb.append("\n\n@interface " + filename + "\n");
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
	private String createObjCProps() {
		StringBuilder sb = new StringBuilder();
		for (HashMap.Entry<String, String> val : getMapVarType().entrySet()) {
			sb.append("\n@property (nonatomic,");
			if (val.getValue().contains("*")) {
				sb.append("strong)");
			} else {
				sb.append("assign)");
			}
			sb.append(" " + val.getValue() + " ");
			sb.append(val.getKey() + ";");
		}
		return sb.toString();
	}
	// ************ GENERATE H FILE END ************

}
