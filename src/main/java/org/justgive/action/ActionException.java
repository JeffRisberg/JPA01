package org.justgive.action;

import org.justgive.exceptions.JustGiveException;

/**
 * Created by IntelliJ IDEA.
 * User: peter c
 * Date: Feb 28, 2008
 * Time: 9:44:03 AM
 */
public class ActionException extends JustGiveException
{
	public ActionException(String string)
	{
		super(string);
	}

	public ActionException(Exception exception)
	{
		super(exception);
	}
}
