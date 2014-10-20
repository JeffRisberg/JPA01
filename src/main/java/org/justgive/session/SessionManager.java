package org.justgive.session;

import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;
import org.justgive.models.JGSession;
import org.justgive.services.JGSessionManager;

import java.util.Arrays;
import java.util.List;

/**
 * Obtains and creates sessions.  Handles user authentication.
 * <p/>
 * This is a subset of the real code.
 *
 * @author Curtis
 * @since 2007
 */
public class SessionManager {
    private static Logger jgLog = LoggerFactory.getLogger(SessionManager.class);

    private JGSessionManager jgSessionManager = null;

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
        this.jgSessionManager = new JGSessionManager();
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }

        return instance;
    }

    /**
     * fetch a list
     */
    public List<JGSession> findAll() {
        return jgSessionManager.findAll();
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
            jgLog.debug("Loading JGSsession " + cookieID);
            //jgSession = DatabaseItemManager.getSessionInstance().find(JGSession.class, "jSessionId", cookieID);
            //if (jgSession != null) jgLog.debug("JGSession loaded " + jgSession.getId());
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
        try {
            jgSessionManager.save(jgSession);
        } catch (Exception e) {
            throw new SessionException(e);
        }

        return jgSession;
    }

    /**
     * Persists the session.  If the JGSession argument has no id value, the record is created.
     *
     * @param jgSession the JGSession object to persist
     * @throws SessionException if any exception encountered.
     */
    public void saveSession(JGSession jgSession) throws SessionException {
        //DBTransaction dbTransaction = null;
        try {
            //dbTransaction = DBSessionFactory.getInstance().newTransaction();
            //dbTransaction.begin();

            // Create or retrieve persisted session depending on id value
            JGSession persistedSession = null;
            if (jgSession.getId() == null) {
                persistedSession = new JGSession();
                //persistedSession.setDateCreated();
            } else {
                persistedSession = jgSessionManager.findOne(jgSession.getId());
                if (persistedSession == null) {
                    persistedSession = new JGSession();
                    //persistedSession.setDateCreated();
                }
            }

            // Copy the session values to save over to the persisted session
            //persistedSession.setLastUpdated();

            persistedSession.setJSessionId(jgSession.getJSessionId());
            persistedSession.setReturnId(jgSession.getReturnId());
            persistedSession.setDonorId(jgSession.getDonorId());
            persistedSession.setAffiliateId(jgSession.getAffiliateId());
            persistedSession.setAuthenticated(jgSession.isAuthenticated());

            /*
            Tracking tracking = jgSession.getTracking();
            if (tracking != null) {
                persistedSession.setTracking(tracking);
            } else {
                persistedSession.setTracking(null);
            }
            */

            // Create or update session
            jgSessionManager.save(persistedSession);
            /*
            if (jgSession.getId() == null) {
                dbTransaction.create(persistedSession);
            } else {
                dbTransaction.update(persistedSession);
            }

            dbTransaction.commit();
            */
        } catch (Exception e) {
            e.printStackTrace();
            throw new SessionException(e);
        } finally {
            /*
            if (dbTransaction != null) {
                try {
                    dbTransaction.close();
                } catch (DBException e1) {
                    jgLog.warn(e1);
                }
            }
            */
        }
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
