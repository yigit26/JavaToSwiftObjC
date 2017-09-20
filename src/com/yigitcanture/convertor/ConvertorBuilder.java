package com.yigitcanture.convertor;

import com.yigitcanture.convertor.enums.Language;
import com.yigitcanture.convertor.exception.PathOrNameNotFoundException;
import com.yigitcanture.convertor.exception.UnknownLanguageException;
import com.yigitcanture.convertor.model.Convertor;

public class ConvertorBuilder {
	private String destinationPath;
	private String sourcePath;
	private String companyName;
	private String projectName;

	/**
	 * @param destinationPath
	 *            the destinationPath to set
	 * @returns {@link ConvertorBuilder}
	 */
	public ConvertorBuilder setDestinationPath(String destinationPath) {
		this.destinationPath = destinationPath;
		return this;
	}

	/**
	 * @param sourcePath
	 *            the sourcePath to set
	 * @returns {@link ConvertorBuilder}
	 */
	public ConvertorBuilder setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
		return this;
	}

	/**
	 * @param companyName
	 *            the companyName to set
	 * @returns {@link ConvertorBuilder}
	 */
	public ConvertorBuilder setCompanyName(String companyName) {
		this.companyName = companyName;
		return this;
	}

	/**
	 * @param projectName
	 *            the projectName to set
	 * @returns {@link ConvertorBuilder}
	 */
	public ConvertorBuilder setProjectName(String projectName) {
		this.projectName = projectName;
		return this;
	}

	/***
	 * This method will create the converter according to language
	 * 
	 * @param language
	 *            {@link Language}
	 * @return {@link IConvertor}
	 */
	public IConvertor buildGenerator(Language language) {
		if (destinationPath == null || sourcePath == null || companyName == null || projectName == null
				|| destinationPath.trim().isEmpty() || sourcePath.trim().isEmpty() || companyName.trim().isEmpty()
				|| projectName.trim().isEmpty()) {
			throw new PathOrNameNotFoundException();
		}
		if (language == null) {
			throw new UnknownLanguageException();
		}
		Convertor convertor = new Convertor(destinationPath, sourcePath, companyName, projectName);
		if(language.equals(Language.OBJECTIVEC)) {
			return new ObjectiveCConvertor(convertor);
		} else if(language.equals(Language.SWIFT)) {
			return new SwiftConvertor(convertor);
		} else {
			return null;
		}

	}
}