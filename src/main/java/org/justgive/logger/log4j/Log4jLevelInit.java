package org.justgive.logger.log4j;

import org.justgive.logger.Level;
import org.justgive.logger.LevelInit;

/**
 * Created by IntelliJ IDEA.
 * Donor: peter c
 * Date: Jan 23, 2008
 * Time: 10:20:59 PM
 */
public class Log4jLevelInit
		implements LevelInit
{
	public void initLevels()
	{
		Level.TRACE = toLevel(org.apache.log4j.Level.ALL);
		Level.DEBUG = toLevel(org.apache.log4j.Level.DEBUG);
		Level.INFO = toLevel(org.apache.log4j.Level.INFO);
		Level.WARN = toLevel(org.apache.log4j.Level.WARN);
		Level.ERROR = toLevel(org.apache.log4j.Level.ERROR);
		Level.FATAL = toLevel(org.apache.log4j.Level.FATAL);
		Level.OFF = toLevel(org.apache.log4j.Level.OFF);
	}

	public Level toLevel(Object o)
	{
		org.apache.log4j.Level l = (org.apache.log4j.Level) o;
		return new Level(l.toInt(),l.toString());
	}


}
