package org.justgive.exceptions;


public class JustGiveException extends Exception {
    public JustGiveException(String msg) {
        super(msg);
    }

    public JustGiveException(Exception e) {
        super(e);
    }
}
