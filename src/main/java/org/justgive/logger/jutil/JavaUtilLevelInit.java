package org.justgive.logger.jutil;

import org.justgive.logger.Level;
import org.justgive.logger.LevelInit;

/**
 * Created by IntelliJ IDEA.
 * User: peter c
 * Date: Jan 25, 2008
 * Time: 1:15:05 PM
 */
public class JavaUtilLevelInit
		implements LevelInit
{
	public void initLevels()
	{
		Level.TRACE = toLevel(java.util.logging.Level.FINEST);
		Level.DEBUG = toLevel(java.util.logging.Level.FINE);
		Level.INFO = toLevel(java.util.logging.Level.INFO);
		Level.WARN = toLevel(java.util.logging.Level.WARNING);
		Level.ERROR = toLevel(java.util.logging.Level.SEVERE);
		Level.FATAL = toLevel(java.util.logging.Level.SEVERE);
		Level.OFF = toLevel(java.util.logging.Level.OFF);
	}

	public Level toLevel(Object o)
	{
	    java.util.logging.Level l = (java.util.logging.Level) o;
		return new Level(l.intValue(),l.toString());
	}
}
