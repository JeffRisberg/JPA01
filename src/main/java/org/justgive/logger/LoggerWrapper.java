package org.justgive.logger;

import java.io.Writer;

/**
 * Created by IntelliJ IDEA.
 * User: peter c
 * Date: Jan 9, 2008
 * Time: 11:36:23 AM
 */
public abstract class LoggerWrapper
		extends AbstractLogger
{
	protected AbstractLogger logger;

	protected LoggerWrapper() {}

	public LoggerWrapper(AbstractLogger logger)
	{
		if (logger == null)
		{
            throw new IllegalArgumentException("LoggerWrapper: logger is null");
		}
		this.logger = logger;
	}

	protected String getMessage()
	{
		return logger.getMessage();
	}

	protected void setMessage(String message)
	{
		logger.setMessage(message);
	}

	protected Writer getWriter()
	{
		return logger.getWriter();
	}

	protected void setWriter(Writer writer)
	{
		logger.setWriter(writer);
	}

	public Level getThreshold()
	{
		return logger.getThreshold();
	}

	public void setThreshold(Level threshold)
	{
		logger.setThreshold(threshold);
	}

	protected void processMessage(Level level)
			throws LoggingException
	{
		writeMessage(level);
		logger.processMessage(level);
	}
}
