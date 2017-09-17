# JavaToSwiftObjC

[![Build Status](https://travis-ci.org/yigit26/JavaToSwiftObjC.svg?branch=master)](https://travis-ci.org/yigit26/JavaToSwiftObjC)
[![codecov](https://codecov.io/gh/yigit26/JavaToSwiftObjC/branch/master/graph/badge.svg)](https://codecov.io/gh/yigit26/JavaToSwiftObjC)

## Description
  This library is used to convert java entity classes to swift-objC classes.
  

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
