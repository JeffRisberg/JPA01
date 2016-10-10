package org.justgive.logger;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * Donor: peter c
 * Date: Jan 23, 2008
 * Time: 10:17:53 PM
 */
public class Level
{
	public static Level TRACE;
	public static Level DEBUG;
	public static Level INFO;
	public static Level WARN;
	public static Level ERROR;
	public static Level FATAL;
	public static Level OFF;

	public static HashMap<String,Level> labelMap = new HashMap<String, Level>();
	public static HashMap<Integer,Level> levelMap = new HashMap<Integer, Level>();

	protected int level;
	protected String label;

	protected Level() {}

	public Level(int level, String label)
	{
		this.level = level;
		this.label = label;
	}

	public int toInt()
	{
		return level;
	}

	public String toString()
	{
		return label;
	}

	public boolean equals(Object obj)
	{
		Level l = (Level) obj;
		return this.toInt() == l.toInt();
	}

	public static Level getLevel(String label)
	{
		Level lvl = labelMap.get(label.toUpperCase());
		if (lvl == null) lvl = Level.WARN;
		return lvl;
	}

	public static Level getLevel(int level)
	{
		Level lvl = levelMap.get(level);
		if (lvl == null) lvl = Level.WARN;
		return lvl;
	}

	public static void mapLevels()
	{
		labelMap.put("TRACE", Level.TRACE);
		labelMap.put("DEBUG", Level.DEBUG);
		labelMap.put("INFO", Level.INFO);
		labelMap.put("WARN", Level.WARN);
		labelMap.put("ERROR", Level.ERROR);
		labelMap.put("FATAL", Level.FATAL);
		labelMap.put("OFF", Level.OFF);

		levelMap.put(Level.TRACE.toInt(), Level.TRACE);
		levelMap.put(Level.DEBUG.toInt(), Level.DEBUG);
		levelMap.put(Level.INFO.toInt(), Level.INFO);
		levelMap.put(Level.WARN.toInt(), Level.WARN);
		levelMap.put(Level.ERROR.toInt(), Level.ERROR);
		levelMap.put(Level.FATAL.toInt(), Level.FATAL);
		levelMap.put(Level.OFF.toInt(), Level.OFF);
	}

}
