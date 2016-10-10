package org.justgive.logger.wrappers;

import org.justgive.logger.AbstractLogger;
import org.justgive.logger.Level;
import org.justgive.logger.LoggerWrapper;
import org.justgive.logger.LoggingException;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * Donor: peter c
 * Date: Jan 14, 2008
 * Time: 4:31:06 PM
 */
public class HyphenWrapper
		extends LoggerWrapper
{
	public HyphenWrapper(AbstractLogger logger)
	{
		super(logger);
	}

	protected void writeMessage(Level level)
			throws LoggingException
	{
		try
		{
			getWriter().write("- ");
		}
		catch (IOException e)
		{
			throw new LoggingException(e.getMessage());
		}
	}
}
