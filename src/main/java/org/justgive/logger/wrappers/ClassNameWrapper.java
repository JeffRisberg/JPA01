package org.justgive.logger.wrappers;

import org.justgive.logger.AbstractLogger;
import org.justgive.logger.Level;
import org.justgive.logger.LoggerWrapper;
import org.justgive.logger.LoggingException;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: peter c
 * Date: Jan 9, 2008
 * Time: 11:58:11 AM
 */
public class ClassNameWrapper
		extends LoggerWrapper
{
	private String className;

    public ClassNameWrapper(AbstractLogger logger, String className)
	{
		super(logger);
		if (className == null)
		{
            throw new IllegalArgumentException("ClassNameWrapper: className is null");
		}
		this.className = className;
	}

	public void writeMessage(Level level)
		throws LoggingException
	{
		try
		{
			getWriter().write(className + ": ");
		}
		catch (IOException e)
		{
			throw new LoggingException(e.getMessage());
		}
	}
}
