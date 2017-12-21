package org.justgive.action;

/**
 */
public class ActionNotFoundException
	extends ActionException {

	public ActionNotFoundException(String string) {
		super(string);
	}

	public ActionNotFoundException(Exception exception) {
		super(exception);
	}
}
