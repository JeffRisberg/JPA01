package org.justgive.logger.justgive;

import org.justgive.logger.Level;
import org.justgive.logger.LoggingException;

/**
 * Created by IntelliJ IDEA.
 * Donor: peter c
 * Date: Oct 15, 2008
 * Time: 10:55:47 AM
 *
 * errors go to stderr
 * out put goes to stdout
 */
public class UnixLogger
	extends JustGiveLogger
{

	public UnixLogger()
	{
		super();
	}

	protected void out(Level level) throws LoggingException
	{
		if (level.toInt() >= Level.ERROR.toInt())
		{
			System.err.println(getWriter().toString());
		}
		else
		{
			System.out.println(getWriter().toString());
		}
		initializeLogger();
	}
}
