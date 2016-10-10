package org.justgive.logger.justgive;

import org.justgive.logger.Level;
import org.justgive.logger.LoggingException;

/**
 * Created by IntelliJ IDEA.
 * Donor: peter c
 * Date: Jan 9, 2008
 * Time: 11:49:28 AM
 */
public class SystemOutLogger
	extends JustGiveLogger
{
	public SystemOutLogger()
	{
		super();
	}

	protected void out(Level level) throws LoggingException
	{
		System.out.println(getWriter().toString());
		initializeLogger();
	}

}
