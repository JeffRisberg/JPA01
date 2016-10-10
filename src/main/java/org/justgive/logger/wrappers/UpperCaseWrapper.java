package org.justgive.logger.wrappers;

import org.justgive.logger.AbstractLogger;
import org.justgive.logger.Level;
import org.justgive.logger.LoggerWrapper;
import org.justgive.logger.LoggingException;

/**
 * Created by IntelliJ IDEA.
 * Donor: peter c
 * Date: Jan 14, 2008
 * Time: 4:31:06 PM
 */
public class UpperCaseWrapper
		extends LoggerWrapper
{
	public UpperCaseWrapper(AbstractLogger logger)
	{
		super(logger);
	}

	protected void writeMessage(Level level)
			throws LoggingException
	{
		try
		{
			setMessage(getMessage().toUpperCase());
		}
		catch (Exception e)
		{
			throw new LoggingException(e.getMessage());
		}
	}

}
