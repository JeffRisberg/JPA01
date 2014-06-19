package org.justgive.logger;

import java.io.Writer;

/**
 * Created by IntelliJ IDEA.
 * User: peter c
 * Date: Jan 15, 2008
 * Time: 1:45:06 PM
 */
public abstract class BaseLogger
	extends AbstractLogger
{
	private String message;
	private Writer writer;
	private Level threshold;
	protected static Level defaultThreshold;

	public BaseLogger()
	{
		initializeLevels();
		initializeLogger();
	}

	protected String getMessage()
	{
		return message;
	}

	protected void setMessage(String message)
	{
		this.message = message;
	}

	protected Writer getWriter()
	{
		return writer;
	}

	protected void setWriter(Writer writer)
	{
		this.writer = writer;
	}

	public Level getThreshold()
	{
		return (threshold == null) ? defaultThreshold : threshold;
	}

	public void setThreshold(Level threshold)
	{
		this.threshold = threshold;
	}

	protected void processMessage(Level level)
			throws LoggingException
	{
		writeMessage(level);
		out(level);
	}

	protected void initializeLevels()
	{
		getLevelInit().initLevels();
	}

	protected abstract void out(Level level) throws LoggingException;
	
	protected abstract void initializeLogger();

	protected abstract LevelInit getLevelInit();
}
