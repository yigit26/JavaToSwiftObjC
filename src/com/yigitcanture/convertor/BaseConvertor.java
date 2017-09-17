package com.yigitcanture.convertor;

import com.yigitcanture.convertor.model.Convertor;

class BaseConvertor {
	Convertor convertor;
	IConvertorCallback callback;
	
	public BaseConvertor(Convertor convertor) {
		this.convertor = convertor;
	}
}
