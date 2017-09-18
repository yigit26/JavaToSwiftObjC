package com.yigitcanture.convertor.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariablesUtils {
	private static VariablesUtils instance;

	private static HashMap<String, String> objeCVariableMap = new HashMap<>();
	private static HashMap<String, String> swiftVariableMap = new HashMap<>();

	private VariablesUtils() {
		this.setupObjCVariables();
		this.setupSwiftVariables();
	}

	public Map<String, String> getObjCVariableMap() {
		return VariablesUtils.objeCVariableMap;
	}

	private Map<String, String> getSwiftVariableMap() {
		return VariablesUtils.swiftVariableMap;
	}

	public String getTypeSwiftFromJava(String javaType) {
		if (VariablesUtils.getInstance().getSwiftVariableMap().containsKey(javaType)) {
			return VariablesUtils.getInstance().getSwiftVariableMap().get(javaType);
		}
		if (javaType.contains("<")) {
			if (javaType.contains(",")) {
				Pattern pattern = Pattern.compile(StringConstants.PATTERN_MAP);
				Matcher matcher = pattern.matcher(javaType);
				String type0 = null;
				String type1 = null;
				while (matcher.find()) {
					type0 = getTypeSwiftFromJava(matcher.group(1));
					type1 = getTypeSwiftFromJava(matcher.group(2));
				}
				return "[" + type0 + " : " + type1 + "]";
			} else {
				Pattern pattern = Pattern.compile(StringConstants.PATTERN_LIST);
				Matcher matcher = pattern.matcher(javaType);
				String type0 = null;
				while (matcher.find()) {
					type0 = getTypeSwiftFromJava(matcher.group(1));
				}
				return "[" + type0 + "]";
			}
		}
		return javaType;
	}

	public String getTypeObjCFromJava(String javaType) {
		if (VariablesUtils.getInstance().getObjCVariableMap().containsKey(javaType)) {
			return VariablesUtils.getInstance().getObjCVariableMap().get(javaType);
		}
		if (javaType.contains("<")) {
			if (javaType.contains(",")) {
				return "NSMutableDictionary*";
			} else {
				return "NSMutableArray*";
			}

		}
		return javaType + "*";
	}

	private void setupSwiftVariables() {
		String int64 = "Int64";
		swiftVariableMap.put("Integer", "Int32");
		swiftVariableMap.put("int", "CInt");
		swiftVariableMap.put("boolean", "CBool");
		swiftVariableMap.put("Boolean", "Bool");
		swiftVariableMap.put("Long", int64);
		swiftVariableMap.put("Short", "Int16");
		swiftVariableMap.put("Byte", "Int8");
		swiftVariableMap.put("BigInteger", int64);
		swiftVariableMap.put("BigDecimal", int64);
		swiftVariableMap.put("int", "CInt");
		swiftVariableMap.put("long", "CLong");
		swiftVariableMap.put("double", "CDouble");
		swiftVariableMap.put("float", "CFloat");
		swiftVariableMap.put("short", "CShort");
		swiftVariableMap.put("byte", "CShort");
	}

	private void setupObjCVariables() {
		String nsNumber = "NSNumber*";
		objeCVariableMap.put("String", "NSString*");
		objeCVariableMap.put("Character", "NSString*");
		objeCVariableMap.put("Date", "NSDate*");
		objeCVariableMap.put("boolean", "BOOL");
		objeCVariableMap.put("Boolean", "BOOL");
		objeCVariableMap.put("Integer", nsNumber);
		objeCVariableMap.put("Double", nsNumber);
		objeCVariableMap.put("Float", nsNumber);
		objeCVariableMap.put("Long", nsNumber);
		objeCVariableMap.put("Short", "NSInteger");
		objeCVariableMap.put("Byte", "NSInteger*");
		objeCVariableMap.put("BigInteger", nsNumber);
		objeCVariableMap.put("BigDecimal", "NSDecimal");
		objeCVariableMap.put("int", "int");
		objeCVariableMap.put("long", "long");
		objeCVariableMap.put("double", "double");
		objeCVariableMap.put("float", "float");
		objeCVariableMap.put("short", "short");
		objeCVariableMap.put("byte", "int");
	}

	public static VariablesUtils getInstance() {
		if (instance == null) {
			instance = new VariablesUtils();
		}
		return instance;
	}

}
