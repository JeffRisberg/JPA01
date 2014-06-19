package org.justgive.properties;

import org.justgive.exceptions.JustGiveRuntimeException;

/**
 * Runtime exception called when an attempt is made to load a properties file that
 * isn't found.
 * 
 * User: curtis
 * Date: Aug 14, 2007
 * Time: 3:06:06 PM
 */
public class PropertyFileNotFoundException extends JustGiveRuntimeException {
    public PropertyFileNotFoundException(String message){
        super(message);
    }

    public PropertyFileNotFoundException(Exception e){
        super(e);
    }
}
