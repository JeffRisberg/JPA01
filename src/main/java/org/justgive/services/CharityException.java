package org.justgive.services;

import org.justgive.exceptions.JustGiveException;

/**
 * Donor: curtis
 * Date: Jan 6, 2009
 * Time: 2:39:47 PM
 */
public class CharityException extends JustGiveException {
	public CharityException(String message){
		super(message);
	}

    public CharityException(Exception e){
		super(e);
	}
}
