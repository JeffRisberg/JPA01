package org.justgive.properties;

import org.justgive.exceptions.JustGiveRuntimeException;

/**
 * Runtime exception thrown when a required JustGive Property is not found.
 * The author of an application decides when a particular
 * property is required, and so when not found, throws this exception.
 *
 * Donor: curtis
 * Date: Aug 14, 2007
 * Time: 3:19:18 PM
 */
public class RequiredPropertyNotFoundException extends JustGiveRuntimeException {
    public RequiredPropertyNotFoundException(String message){
        super(message);
    }

    public RequiredPropertyNotFoundException(Exception e){
        super(e);
    }
}
