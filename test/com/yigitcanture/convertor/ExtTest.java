package com.yigitcanture.convertor;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.yigitcanture.convertor.ConvertorBuilder;
import com.yigitcanture.convertor.IConvertor;
import com.yigitcanture.convertor.enums.Language;

public class ExtTest {

	@Test
	public void test() {
		assertTrue(true);
	}

	public IConvertor createValidObjCConvertor() {
		IConvertor generator = new ConvertorBuilder().setCompanyName("Yigit Can Ture")
				.setProjectName("Example Project").setDestinationPath("./dest").setSourcePath("./source")
				.buildGenerator(Language.OBJECTIVEC);
		return generator;
	}

	public IConvertor createNonValidObjCConvertor() {
		IConvertor generator = new ConvertorBuilder().setCompanyName(null).setDestinationPath(null)
				.setSourcePath(null).buildGenerator(Language.OBJECTIVEC);
		return generator;
	}

	public IConvertor createNonAccessibleObjCConvertor() {
		IConvertor generator = new ConvertorBuilder().setCompanyName("Yigit Can Ture")
				.setDestinationPath("/").setSourcePath("./source").setProjectName("Project").buildGenerator(Language.OBJECTIVEC);
		return generator;
	}
	
	public IConvertor createValidSwiftConvertor() {
		IConvertor generator = new ConvertorBuilder().setCompanyName("Yigit Can Ture")
				.setProjectName("Example Project").setDestinationPath("./dest").setSourcePath("./source")
				.buildGenerator(Language.SWIFT);
		return generator;
	}

	public IConvertor createNonValidSwiftConvertor() {
		IConvertor generator = new ConvertorBuilder().setCompanyName("").setDestinationPath("")
				.setSourcePath("").setProjectName("Ali").buildGenerator(Language.SWIFT);
		return generator;
	}

	public IConvertor createNonAccessibleSwiftConvertor() {
		IConvertor generator = new ConvertorBuilder().setCompanyName("Yigit Can Ture")
				.setDestinationPath("/").setSourcePath("./source").setProjectName("Test").buildGenerator(Language.OBJECTIVEC);
		return generator;
	}
	
	
	public IConvertor createValidConvertorWithoutLanguage() {
		IConvertor generator = new ConvertorBuilder().setCompanyName("Yigit Can Ture")
				.setProjectName("Example Project").setDestinationPath("./dest").setSourcePath("./source")
				.buildGenerator(null);
		return generator;
	}

}
