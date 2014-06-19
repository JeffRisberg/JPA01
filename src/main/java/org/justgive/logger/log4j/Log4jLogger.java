package org.justgive.logger.log4j;

import org.justgive.logger.BaseLogger;
import org.justgive.logger.Level;
import org.justgive.logger.LevelInit;
import org.justgive.logger.LoggingException;

import java.io.StringWriter;

/**
 * Created by IntelliJ IDEA.
 * User: peter c
 * Date: Jan 9, 2008
 * Time: 12:03:38 PM
 */
public class Log4jLogger
		extends BaseLogger
{
	private org.apache.log4j.Logger log4jLogger;
	private static Log4jLevelInit levelInit = new Log4jLevelInit();

	protected LevelInit getLevelInit()
	{
		return levelInit;
	}

	public Log4jLogger()
	{
		this(null);
	}

	public Log4jLogger(String className)
	{
		super();
		log4jLogger = (className != null) ? org.apache.log4j.Logger.getLogger(className) : org.apache.log4j.Logger.getRootLogger();
		setThreshold(levelInit.toLevel(log4jLogger.getEffectiveLevel()));
	}

	protected void out(Level level) throws LoggingException
	{
		log4jLogger.log(org.apache.log4j.Level.toLevel(level.toInt()), getWriter().toString());
		initializeLogger();
	}
	
	protected void initializeLogger()
	{
		setWriter(new StringWriter());
		setMessage(null);
		defaultThreshold = Level.DEBUG;
	}
}
