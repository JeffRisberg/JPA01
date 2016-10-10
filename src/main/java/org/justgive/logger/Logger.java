package org.justgive.logger;

/**
 * Created by IntelliJ IDEA.
 * Donor: peter c
 * Date: Jan 16, 2008
 * Time: 1:03:07 PM
 */
public interface Logger
{
    public void log(Object o);

	public void trace(Object o);

	public void debug(Object o);

	public void info(Object o);

	public void warn(Object o);

	public void error(Object o);

	public void fatal(Object o);

    public boolean isTraceEnabled();

    public boolean isDebugEnabled();

    public boolean isInfoEnabled();

    public boolean isWarnEnabled();

    public boolean isErrorEnabled();

    public boolean isFatalEnabled();
}
