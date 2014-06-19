package org.justgive.action;

/**
 * <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
 * <html>
 * <head>
 * <!--
 * <p/>
 * Copyright 2011 JustGive.org, 214 Sutter st, Suite #10,
 * San Francisco, Ca, 94808, U.S.A.  All Rights Reserved.
 * <p/>
 * This software is the confidential and proprietary information of
 * JustGive.org ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with JustGive.
 * <p/>
 * CopyrightVersion 1.0
 * <p/>
 * -->
 * </head>
 * <body bgcolor="white">
 * <p/>
 * <p/>
 * </body>
 * </html>
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
