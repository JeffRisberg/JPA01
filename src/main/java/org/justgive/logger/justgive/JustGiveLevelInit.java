package org.justgive.logger.justgive;

import org.justgive.logger.Level;
import org.justgive.logger.LevelInit;

/**
 * Created by IntelliJ IDEA.
 * Donor: peter c
 * Date: Jan 24, 2008
 * Time: 4:03:26 PM
 */
public class JustGiveLevelInit
		implements LevelInit
{
	public void initLevels()
	{
		Level.TRACE = new Level(Integer.MIN_VALUE, "TRACE");
		Level.DEBUG = new Level(0,"DEBUG");
		Level.INFO = new Level(1000,"INFO");
		Level.WARN = new Level(2000,"WARN");
		Level.ERROR = new Level(3000,"ERROR");
		Level.FATAL = new Level(4000,"FATAL");
		Level.OFF = new Level(Integer.MAX_VALUE,"OFF");

		Level.mapLevels();
	}

	public Level toLevel(Object o)
	{
	    org.justgive.logger.Level l = (org.justgive.logger.Level) o;
		return new Level(l.toInt(),l.toString());
	}
}
