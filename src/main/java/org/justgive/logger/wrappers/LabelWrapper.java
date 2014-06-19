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
 * Time: 12:40:11 PM
 */
public class LabelWrapper
		extends LoggerWrapper
{
	public LabelWrapper(AbstractLogger logger)
	{
		super(logger);
	}

	protected void writeMessage(Level level)
		throws LoggingException
	{
		try
		{
			getWriter().write(level.toString() + ": ");
		}
		catch (IOException e)
		{
			throw new LoggingException(e.getMessage());
		}
	}
}
