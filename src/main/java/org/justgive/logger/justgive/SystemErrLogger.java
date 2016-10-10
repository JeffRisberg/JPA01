package org.justgive.logger.justgive;

import org.justgive.logger.Level;
import org.justgive.logger.LoggingException;

/**
 * Created by IntelliJ IDEA.
 * Donor: peter c
 * Date: Oct 15, 2008
 * Time: 10:46:30 AM
 */
public class SystemErrLogger
	extends JustGiveLogger
{

	public SystemErrLogger()
	{
		super();
	}

	protected void out(Level level) throws LoggingException
	{
		System.err.println(getWriter().toString());
		initializeLogger();
	}
}
