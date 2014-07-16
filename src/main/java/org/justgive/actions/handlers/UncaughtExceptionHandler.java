package org.justgive.actions.handlers;

import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Handles exceptions that are not caught in the servlet environment.
 * <p/>
 * Created by IntelliJ IDEA.
 * User: curtis
 * Date: 3/27/14
 * Time: 2:16 PM
 */
public class UncaughtExceptionHandler {
    private static Logger jgLog = LoggerFactory.getLogger(UncaughtExceptionHandler.class);

    public void uncaughtException(Throwable e) {
        // log the exception
        jgLog.error(e);

        // get the hostname, send an email
        String subject = "Uncaught exception: " + e.getClass().getName();

        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        String message = errors.toString();

        //try {
        //    new NotificationEmail().send(subject, message);
        //} catch (Exception e1) {
        jgLog.error(message);
        //}
    }
}
