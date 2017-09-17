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

	public String getTypeSwiftToJava(String javaType) {
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
					type0 = getTypeSwiftToJava(matcher.group(1));
					type1 = getTypeSwiftToJava(matcher.group(2));
				}
				return "[" + type0 + " : " + type1 + "]";
			} else {
				Pattern pattern = Pattern.compile(StringConstants.PATTERN_LIST);
				Matcher matcher = pattern.matcher(javaType);
				String type0 = null;
				while (matcher.find()) {
					type0 = getTypeSwiftToJava(matcher.group(1));
				}
				return "[" + type0 + "]";
			}
		}
		return javaType;
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
		String nsNumber = " NSNumber*";
		objeCVariableMap.put("private String", StringConstants.STRONG + " NSString*");
		objeCVariableMap.put("private Character", StringConstants.STRONG + " NSString*");
		objeCVariableMap.put("private Date", StringConstants.STRONG + " NSDate*");
		objeCVariableMap.put("private boolean", StringConstants.ASSIGN + " BOOL");
		objeCVariableMap.put("private Boolean", StringConstants.ASSIGN + " BOOL");
		objeCVariableMap.put("private Integer", StringConstants.STRONG + nsNumber);
		objeCVariableMap.put("private Double", StringConstants.STRONG + nsNumber);
		objeCVariableMap.put("private Float", StringConstants.STRONG + nsNumber);
		objeCVariableMap.put("private Long", StringConstants.STRONG + nsNumber);
		objeCVariableMap.put("private Short", StringConstants.ASSIGN + " NSInteger");
		objeCVariableMap.put("private Byte", StringConstants.STRONG + " NSInteger*");
		objeCVariableMap.put("private BigInteger", StringConstants.STRONG + nsNumber);
		objeCVariableMap.put("private BigDecimal", StringConstants.ASSIGN + " NSDecimal");
		objeCVariableMap.put("private int", StringConstants.ASSIGN + " int");
		objeCVariableMap.put("private long", StringConstants.ASSIGN + " long");
		objeCVariableMap.put("private double", StringConstants.ASSIGN + " double");
		objeCVariableMap.put("private float", StringConstants.ASSIGN + " float");
		objeCVariableMap.put("private short", StringConstants.ASSIGN + " short");
		objeCVariableMap.put("private byte", StringConstants.ASSIGN + " int");
		objeCVariableMap.put("private List<[A-Za-z_]+>", StringConstants.STRONG + " NSMutableArray*");
		objeCVariableMap.put("private HashMap<[A-Za-z_ ,]+>", StringConstants.STRONG + " NSMutableDictionary*");
		objeCVariableMap.put("private Map<[A-Za-z_ ,]+>", StringConstants.STRONG + " NSMutableDictionary*");
	}

	public static VariablesUtils getInstance() {
		if (instance == null) {
			instance = new VariablesUtils();
		}
		return instance;
	}

}
