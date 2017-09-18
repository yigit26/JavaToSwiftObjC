# JavaToSwiftObjC

[![Build Status](https://travis-ci.org/yigit26/JavaToSwiftObjC.svg?branch=master)](https://travis-ci.org/yigit26/JavaToSwiftObjC)
[![codecov](https://codecov.io/gh/yigit26/JavaToSwiftObjC/branch/master/graph/badge.svg)](https://codecov.io/gh/yigit26/JavaToSwiftObjC)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/244c4c49511d4e31a5c261560fff1b34)](https://www.codacy.com/app/yigit26/JavaToSwiftObjC?utm_source=github.com&utm_medium=referral&utm_content=yigit26/JavaToSwiftObjC&utm_campaign=badger)
## Description
  This library is used to convert java entity classes to swift-objC classes.
  ![SampleObject.java](http://blog.yigitcanture.com/wp-content/uploads/2017/09/WhatsApp-Image-2017-09-17-at-19.21.28.jpeg)

## Usage
  * Creating the convertor :
  ```java
    IConvertor generator = new ConvertorBuilder().setCompanyName("Yigit Can Ture")
				.setProjectName("Example Project").setDestinationPath("./dest").setSourcePath("./source")
				.buildGenerator(Language.OBJECTIVEC);
  ```
  * Then call the 'generator()' method
  ```java
    generator.generate(null);
  ```
    
   OR
    
  ```java
    generator.generate(System.out::println);
  ```
  
## Requirements
 jdk 8+

## Installation

1. Clone the repo.
2. Import your ide with `'pom.xml'`
3. Compile and use it.

## Author

Yigit Can Ture
