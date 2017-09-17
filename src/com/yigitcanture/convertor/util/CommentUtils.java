package com.yigitcanture.convertor.util;

public class CommentUtils {
	
	private CommentUtils() {
		
	}
	
	/***
	 *  
	 * @param fileName Indicates destination file name with extension.
	 * @param companyName 
	 * @param projectName
	 * @return {@link String} returns header comment.
	 */
	public static String createComment(String fileName,String companyName,String projectName) {
		StringBuilder sb = new StringBuilder();
		sb.append(StringConstants.COMMENT + "\n");
		sb.append(StringConstants.COMMENT + "  " + fileName  + "\n");
		sb.append(StringConstants.COMMENT + "  " + projectName + "\n");
		sb.append(StringConstants.COPY_RIGHTS + companyName);
		sb.append(StringConstants.COPY_RIGHTS_END);
		sb.append(StringConstants.GENERATED + companyName);
		sb.append(StringConstants.GENERATED_END);
		sb.append(StringConstants.COMMENT + "\n\n");
		return sb.toString();
	}
}
