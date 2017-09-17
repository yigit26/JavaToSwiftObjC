package com.yigitcanture.convertor.model;

public class Convertor {
	private String destinationPath;
	private String sourcePath;
	private String companyName;
	private String projectName;

	public Convertor(String destinationPath, String sourcePath, String companyName, String projectName) {
		this.destinationPath = destinationPath;
		this.sourcePath = sourcePath;
		this.companyName = companyName;
		this.projectName = projectName;
	}

	public String getDestinationPath() {
		return destinationPath;
	}

	public String getSourcePath() {
		return sourcePath;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getProjectName() {
		return projectName;
	}
}
