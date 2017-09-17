package com.yigitcanture.convertor.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SampleObject {

	private BigDecimal bigDecimal;
	private BigInteger bigInteger;
	private String name;
	private Date date;
	private long longP;
	private List<String> list;
	private HashMap<String, String> hashMap;
	private short shortP;
	private Boolean booleanObj;
	private boolean bool;
	private EmptyObject emptyObject;

	public BigDecimal getBigDecimal() {
		return bigDecimal;
	}

	public void setBigDecimal(BigDecimal bigDecimal) {
		this.bigDecimal = bigDecimal;
	}

	public BigInteger getBigInteger() {
		return bigInteger;
	}

	public void setBigInteger(BigInteger bigInteger) {
		this.bigInteger = bigInteger;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getLongP() {
		return longP;
	}

	public void setLongP(long longP) {
		this.longP = longP;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public Map<String, String> getHashMap() {
		return hashMap;
	}

	public void setHashMap(HashMap<String, String> hashMap) {
		this.hashMap = hashMap;
	}

	public short getShortP() {
		return shortP;
	}

	public void setShortP(short shortP) {
		this.shortP = shortP;
	}

	public Boolean getBooleanObj() {
		return booleanObj;
	}

	public void setBooleanObj(Boolean booleanObj) {
		this.booleanObj = booleanObj;
	}

	public boolean isBool() {
		return bool;
	}

	public void setBool(boolean bool) {
		this.bool = bool;
	}

}