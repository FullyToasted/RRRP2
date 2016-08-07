package net.re_renderreality.rrrp2.utils;

import org.slf4j.Logger;

import net.re_renderreality.rrrp2.RRRP2.DebugLevel;
import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfig;

public class Log {

private static Logger logger;

	public static void setLogger(Logger l) {
		logger = l;
	}
	
	public static void info(String message) {
		if(ReadConfig.getDebugLevel() == DebugLevel.INFO || ReadConfig.getDebugLevel() == DebugLevel.ALL || ReadConfig.getDebugLevel() == DebugLevel.DEBUG) {
			logger.info(message);
		}
	}
	
	public static void warn(String message) {
		logger.warn(message);
		if(ReadConfig.getDebugLevel() == DebugLevel.INFO || ReadConfig.getDebugLevel() == DebugLevel.ALL || ReadConfig.getDebugLevel() == DebugLevel.WARNING || ReadConfig.getDebugLevel() == DebugLevel.DEBUG) {
			logger.warn(message);
		}
	}
	
	public static void severe(String message) {
		if(ReadConfig.getDebugLevel() == DebugLevel.INFO || ReadConfig.getDebugLevel() == DebugLevel.ALL || ReadConfig.getDebugLevel() == DebugLevel.SEVERE || ReadConfig.getDebugLevel() == DebugLevel.DEBUG) {
			logger.error(message);
		}
	}
	
	public static void config(String message) {
		if(ReadConfig.getDebugLevel() == DebugLevel.INFO || ReadConfig.getDebugLevel() == DebugLevel.ALL || ReadConfig.getDebugLevel() == DebugLevel.CONFIG || ReadConfig.getDebugLevel() == DebugLevel.DEBUG) {
			logger.info(message);
		}
	}
	
	public static void debug(String message) {
		if(ReadConfig.getDebugLevel() == DebugLevel.DEBUG || ReadConfig.getDebugLevel() == DebugLevel.ALL) {
			logger.info(message);
		}
	}

	public static void error(String string) {
		logger.error("ERROR: " + string);
	}

	public static void command(String string) {
		if(ReadConfig.getLogCommands()) {
			logger.info(string);
		}
	}

	public static void test(String test) {
		logger.warn(test);
	}	
}
