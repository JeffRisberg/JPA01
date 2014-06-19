package org.justgive.logger.wrappers;

import org.justgive.logger.AbstractLogger;
import org.justgive.logger.Level;
import org.justgive.logger.LoggerWrapper;
import org.justgive.logger.LoggingException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: peter c
 * Date: Oct 8, 2008
 * Time: 10:41:59 AM
 */
public class DateTimeWrapper
		extends LoggerWrapper
{
	public DateTimeWrapper(AbstractLogger logger)
	{
		super(logger);
	}

	protected void writeMessage(Level level)
			throws LoggingException
	{
		try
		{
			getWriter().write(getDateTime() + " ");
		}
		catch (IOException e)
		{
			throw new LoggingException(e.getMessage());
		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("[dd/MMM/yyyy:HH:mm:ss]");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
