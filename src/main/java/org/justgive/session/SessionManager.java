package org.justgive.session;

import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;

import java.util.Arrays;

/**
 * User: curtis
 * Date: Jun 23, 2007
 * Time: 12:24:05 PM
 * <p/>
 * Obtains and creates sessions.  Handles user authentication.
 */
public class SessionManager {
    private static Logger jgLog = LoggerFactory.getLogger(SessionManager.class);

    private static SessionManager instance;

    private static final String INVALID_USERNAME = "Username does not exist.";
    private static final String INVALID_PASSWORD = "Invalid username/password combination.";

    private static byte[] ILLEGAL_REQUEST_CHARS;

    static {
        StringBuilder buff = new StringBuilder();
        for (char i = 0x0000; i < 0x0020; i++) {
            if (i != 0x0009 && i != 0x000A && i != 0x000D) {
                buff.append(i);
            }
        }
        ILLEGAL_REQUEST_CHARS = buff.toString().getBytes();
        Arrays.sort(ILLEGAL_REQUEST_CHARS);
    }

    private SessionManager() {

    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }

        return instance;
    }

    /**
     * Returns the session for the given cookie.  Returns null if the cookie value is not in the
     * database.
     *
     * @param cookieID The cookieID associated with the session.
     * @return JGSession or null if cookieID associated with any session.
     * @throws SessionException when any exception encountered.
     */
    public JGSession getSession(String cookieID) throws SessionException {
        if (cookieID == null) {
            throw new IllegalArgumentException("getSession: Cookie ID is null");
        }

        JGSession jgSession = null;

        try {
            jgLog.debug("Loading JGSsession  " + cookieID);
            //jgSession = DatabaseItemManager.getSessionInstance().find(JGSession.class, "jSessionId", cookieID);
            //if (jgSession != null) jgLog.debug("JGSsession loaded " + jgSession.getId());
        } catch (Exception e) {
            throw new SessionException(e);
        }

        return jgSession;
    }

    /**
     * Creates a new session for the cookie.
     *
     * @param cookieID The cookie ID
     * @return JGSession
     * @throws SessionException if any exception encountered.
     */
    public JGSession newSession(String cookieID) throws SessionException {
        JGSession jgSession = new JGSession();

        jgSession.setJSessionId(cookieID);
        jgSession.setReturnId(cookieID);
        jgSession.setAuthenticated(false);

        // Save to the database
        //try {
        //    DatabaseItemManager.getSessionInstance().create(jgSession);
        //} catch (DBException e) {
        //    throw new SessionException(e);
        //}

        return jgSession;
    }

    /**
     * Persists the session.  If the JGSession argument has no id value, the record is created.
     *
     * @param jgSession the JGSession object to persist
     * @throws SessionException if any exception encountered.
     */
    public void saveSession(JGSession jgSession) throws SessionException {
    }

    /**
     * Marks a session as inactive, in this scenario the authenticated
     * status is set to false, but the donorId remains populate.
     *
     * @param jgSession The session to be cleared.
     * @throws SessionException if any exception is encountered.
     */
    public void setUserInactive(JGSession jgSession) throws SessionException {
        if (jgSession == null) {
            throw new IllegalArgumentException("Null JGSession");
        }

        // Clear session by resetting authentication information.
        jgSession.setAuthenticated(false);
    }
}
