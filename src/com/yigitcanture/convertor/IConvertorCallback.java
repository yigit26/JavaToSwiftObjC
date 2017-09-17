package com.yigitcanture.convertor;

/***
 * This interface is used to notify client.
 * @author yigitcanture
 *
 */
public interface IConvertorCallback {
	/***
	 * @param filename Indicates generated file name. 
	 */
	void notify(String filename);
}
