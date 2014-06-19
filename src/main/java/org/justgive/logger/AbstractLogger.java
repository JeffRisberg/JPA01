package org.justgive.logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Copyright 2011 JustGive.org, 214 Sutter st, Suite #10,
 * San Francisco, Ca, 94808, U.S.A.  All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * JustGive.org ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with JustGive.
 *
 * CopyrightVersion 1.0
 *
 * -->
 *
 * @author Peter Cowan
 */
public abstract class AbstractLogger
    implements Logger {

    protected abstract String getMessage();

    protected abstract void setMessage(String message);

    protected abstract Writer getWriter();

    protected abstract void setWriter(Writer writer);

    public abstract Level getThreshold();

    protected abstract void setThreshold(Level threshold);

    /**
     * processMessage is implemented differently by BaseLogger and LoggerWrapper
     * BaseLogger calls writeMessage() and out()
     * LoggerWrapper calles writeMessage() and logger.writeMessage()
     *
     * @param level the threshold for outputing message
     * @throws LoggingException if there is an exception writing the message
     */
    protected abstract void processMessage(Level level) throws LoggingException;

    public void log(Object o) {
        log(getThreshold(), o);
    }

    public void trace(Object o) {
        log(Level.TRACE, o);
    }

    public void debug(Object o) {
        log(Level.DEBUG, o);
    }

    public void info(Object o) {
        log(Level.INFO, o);
    }

    public void warn(Object o) {
        log(Level.WARN, o);
    }

    public void error(Object o) {
        log(Level.ERROR, o);
    }

    public void fatal(Object o) {
        log(Level.FATAL, o);
    }

    public boolean isTraceEnabled() {
        return getThreshold().toInt() <= Level.TRACE.toInt();
    }

    public boolean isDebugEnabled() {
        return getThreshold().toInt() <= Level.DEBUG.toInt();
    }

    public boolean isInfoEnabled() {
        return getThreshold().toInt() <= Level.INFO.toInt();
    }

    public boolean isWarnEnabled() {
        return getThreshold().toInt() <= Level.WARN.toInt();
    }

    public boolean isErrorEnabled() {
        return getThreshold().toInt() <= Level.ERROR.toInt();
    }

    public boolean isFatalEnabled() {
        return getThreshold().toInt() <= Level.FATAL.toInt();
    }

    protected void log(Level level, Object o) {
        if (getThreshold().toInt() <= level.toInt()) {
            if (getMessage() == null) {
                if (o == null) setMessage("null message");
                else if (o instanceof Throwable) setMessage(getStackTrace((Throwable) o));
                else setMessage(o.toString());
            }

            try {
                processMessage(level);
            } catch (LoggingException e) {
                System.out.println(e);
            }
        }
    }

    protected void writeMessage(Level level)
            throws LoggingException {
        try {
            getWriter().write(getMessage());
        } catch (IOException e) {
            throw new LoggingException(e.getMessage());
        }
    }

    private String getStackTrace(Throwable t) {
        StringWriter w = new StringWriter();

        t.printStackTrace(new PrintWriter(w));
        return w.toString();
    }
}
