package org.justgive.properties;

import org.justgive.exceptions.JustGiveException;

/**
 * Donor: curtis
 * Date: Aug 14, 2007
 * Time: 2:53:12 PM
 */
public class PropertyException extends JustGiveException {
    public PropertyException(String message){
        super(message);
    }

    public PropertyException(Exception e){
        super(e);
    }
}
