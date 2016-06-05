package org.dmc.services.logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.dmc.services.config.Config;
import org.dmc.services.web.exceptions.DMCServiceException;


public class ServiceLogger {

	private static FileHandler logFileHandler;
	private static SimpleFormatter logFileFormatter;
	private static Logger logger;
	private static Logger rootLogger;
	private final String LOGFILE = Config.LOG_FILE;
	private static ServiceLogger serviceLoggerInstance = null;

	protected ServiceLogger() {
		try {
			this.setup();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void log (String logTag, String message) {
		if (serviceLoggerInstance == null) {
			serviceLoggerInstance = new ServiceLogger();
		} 
	    logger.info(logTag + ": " + message);	
	}
	
	/**
	 * Exception Logging
	 * @param logTag
	 * @param e
	 */
	public static void logException (String logTag, DMCServiceException e) {
		String logMessage  = null;
		if (serviceLoggerInstance == null) {
			serviceLoggerInstance = new ServiceLogger();
		}
		
		logMessage += "\n---------------------------------------------------------------------------------------------------------------\n";
		logMessage +="EXCEPTION\n";
		logMessage +="Class: " + logTag + "\n";
		logMessage +="Error: " + e.getError() + "\n";
		logMessage +="HttpStatus Code: " + e.getHttpStatusCode() + "\n";
		logMessage +="Message: " + e.getErrorMessage() + "\n";
		logMessage += "---------------------------------------------------------------------------------------------------------------\n";
		
	    logger.info(logMessage);	
	}
	
	private void setup() throws IOException {
	    logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	    
        //Log to the file by default
	    logger.setLevel(Level.INFO);
	    logFileHandler = new FileHandler(LOGFILE);  
        logger.addHandler(logFileHandler);
        
	    //disable console logging by default
	    logger.setUseParentHandlers(false);
	    
	    //Use the Simple file formatter
        logFileFormatter = new SimpleFormatter();  
        logFileHandler.setFormatter(logFileFormatter); 

        //Log to console if enabled in config
        if (Config.CONSOLE_LOGGING) {
	        ConsoleHandler consoleHandler = new ConsoleHandler();
	        consoleHandler.setLevel(Level.ALL);
	        logger.addHandler(consoleHandler);
        }
	}
}
