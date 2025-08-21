package com.log.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogExample 
{
    private static final Logger logger = LogManager.getLogger(LogExample.class);
	public static void main(String[] args) 
	{

		        logger.trace("This is a TRACE level log");
		        logger.debug("This is a DEBUG level log");
		        logger.info("This is an INFO level log");
		        logger.warn("This is a WARN level log");
		        logger.error("This is an ERROR level log");
		        logger.fatal("This is a FATAL level log");
		    }
		}

	

