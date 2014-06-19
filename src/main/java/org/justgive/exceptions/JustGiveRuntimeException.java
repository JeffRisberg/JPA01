package org.justgive.exceptions;

/**
 * The abstract superclass of all JustGive-defined unchecked exceptions.
 *
 * User: curtis
 * Date: Aug 14, 2007
 * Time: 12:42:54 PM
 */
public abstract class JustGiveRuntimeException extends RuntimeException {
    public JustGiveRuntimeException(String message){
        super(message);
    }

    public JustGiveRuntimeException(Exception e){
        super(e);
    }
}
