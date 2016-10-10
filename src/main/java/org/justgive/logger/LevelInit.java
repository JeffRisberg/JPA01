package org.justgive.logger;

/**
 * Created by IntelliJ IDEA.
 * Donor: peter c
 * Date: Jan 25, 2008
 * Time: 1:13:59 PM
 */
public interface LevelInit
{
	public abstract void initLevels();
	public abstract Level toLevel(Object o);
}
