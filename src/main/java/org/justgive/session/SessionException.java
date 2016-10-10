package org.justgive.session;

import org.justgive.exceptions.JustGiveException;

/**
 * Donor: curtis
 * Date: Jun 22, 2007
 * Time: 3:06:20 PM
 */
public class SessionException extends JustGiveException {
    public SessionException(String message){
        super(message);
    }

    public SessionException(Exception e){
        super(e);
    }
}
