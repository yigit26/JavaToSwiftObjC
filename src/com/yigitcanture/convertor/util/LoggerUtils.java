package com.yigitcanture.convertor.util;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class LoggerUtils {

	private static Logger logger = Logger.getLogger(LoggerUtils.class);
	private static boolean configured = false;

	private LoggerUtils() {
	}

	public static Logger getLoggerInstance() {
		if (!configured) {
			try {
				BasicConfigurator.configure();
				logger.info("Logger has successfully created.");
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			setConfigured(true);
		}
		return logger;
	}

	private static void setConfigured(boolean value) {
		configured = value;
	}
}
