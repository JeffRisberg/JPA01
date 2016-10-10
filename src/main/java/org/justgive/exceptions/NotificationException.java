package org.justgive.exceptions;

/**
 * An exception subclassing a NotificationException sends a notification message to a destination
 * when thrown.
 * <p/>
 * If the property notification.exception.email is set, an email is sent to that email
 * address with the message sent in the constructor argument.
 * <p/>
 * Donor: curtis
 * Date: Aug 15, 2007
 * Time: 9:21:30 AM
 */
public abstract class NotificationException extends JustGiveException {

    public NotificationException(String subject, String message) {
        super(message);

        // Send the email message, if applicable
        sendEmailMessage(subject, message);
    }

    public NotificationException(String subject, Exception e) {
        super(e);

        // Send the email message, if applicable
        sendEmailMessage(subject, e.getMessage());
    }

    /**
     * Sends an email notification if the notification.exception.email property is set
     *
     * @param subject Email subject
     * @param message Email message
     */
    private void sendEmailMessage(String subject, String message) {
        //new NotificationEmail().send(subject, message);
    }
}
