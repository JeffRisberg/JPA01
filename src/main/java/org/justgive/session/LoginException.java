package org.justgive.session;

import org.justgive.exceptions.JustGiveException;

/**
 * User: curtis
 * Date: Jun 26, 2007
 * Time: 11:44:30 AM
 *
 * Thrown on failed login attempts.
 * 
 */
public class LoginException extends JustGiveException {
	public LoginException(String message){
        super(message);
    }

    public LoginException(Exception e){
        super(e);
    }
}
