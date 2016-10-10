package org.justgive.session;

import org.justgive.exceptions.JustGiveException;

/**
 * Donor: curtis
 * Date: Jun 25, 2007
 * Time: 10:43:28 PM
 *
 * Thrown when sessions don't accept cookies.
 */
public class CookieException extends JustGiveException {
    public CookieException(String message){
        super(message);
    }

    public CookieException(Exception e){
        super(e);
    }
}
