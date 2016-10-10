package org.justgive.logger.justgive;

import org.justgive.logger.BaseLogger;
import org.justgive.logger.Level;
import org.justgive.logger.LevelInit;

import java.io.StringWriter;

/**
 * Created by IntelliJ IDEA.
 * Donor: peter c
 * Date: Jan 25, 2008
 * Time: 1:35:09 PM
 */
public abstract class JustGiveLogger
	extends BaseLogger
{
	private static JustGiveLevelInit levelInit = new JustGiveLevelInit();

	protected JustGiveLogger()
	{
		super();
	}

	public LevelInit getLevelInit()
	{
		return levelInit;
	}

	protected void initializeLogger()
	{
		setWriter(new StringWriter());
		setMessage(null);
		defaultThreshold = Level.DEBUG;
	}
}
