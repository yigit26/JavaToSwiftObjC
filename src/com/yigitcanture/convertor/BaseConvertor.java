package com.yigitcanture.convertor;

import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yigitcanture.convertor.model.Convertor;
import com.yigitcanture.convertor.util.StringConstants;

class BaseConvertor {
	Convertor convertor;
	IConvertorCallback callback;
	LinkedHashMap<String, String> mapVarType = new LinkedHashMap<>();
	
	public BaseConvertor(Convertor convertor) {
		this.convertor = convertor;
	}
	
	/***
	 * 
	 * @param textJavaFile
	 *            content of source file.
	 * @return {@link String} returns properties which were found in source file
	 */
	String findProperties(String textJavaFile) {
		Pattern pat = Pattern.compile(StringConstants.PRIVATE_PATTERN);
		Matcher match = pat.matcher(textJavaFile);
		StringBuilder propsBuilder = new StringBuilder();
		while (match.find()) {
			String s = match.group();
			s = s.trim();
			propsBuilder.append(s);
			propsBuilder.append("\n");
		}
		return propsBuilder.toString();
	}
}
