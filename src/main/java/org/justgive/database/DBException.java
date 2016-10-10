package org.justgive.database;

import org.justgive.exceptions.JustGiveException;

/**
 * Donor: curtis
 * Date: Jun 18, 2007
 * Time: 10:55:34 AM
 */
public class DBException extends JustGiveException {
    public DBException(String message){
        super(message);
    }

    public DBException(Exception e){
        super(e);
    }
}
