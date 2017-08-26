package ch.blutch.alloblutch.utils;

import org.apache.log4j.Logger;

public class Log4JUtils {

	private static Logger rootLogger;
	
	public static Logger getLogger() {
		if (rootLogger == null) {
			setUp();
		}
		
		return rootLogger;
	}
	
	private static void setUp() {
		rootLogger = Logger.getRootLogger();
	}
	
}
