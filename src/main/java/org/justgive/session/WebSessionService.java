package org.justgive.session;

import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;
import org.justgive.models.JGSession;
import org.justgive.util.StringUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Calendar;

/**
 * The WebSessionService is a mediator between a web application and the session handling packages.
 * <p/>
 * Sessions are propagated in the data store.
 * <p/>
 * Session attributes are also maintained in the data store.  The Object values are serialized.
 * <p/>
 *
 * @author Curtis
 * @since 2007
 */
public class WebSessionService {
    private static Logger jgLog = LoggerFactory.getLogger(WebSessionService.class);

    private static WebSessionService instance;

    // Session attribute names
    public static final String SESSION_ATTRIBUTE_NAME = "tracker";
    private static final String DONOR_ATTRIBUTE_NAME = "user";
    private static final String AFFILIATE_ATTRIBUTE_NAME = "affiliate";

    // The session cookie name
    private static final String SESSION_COOKIE_NAME = "jreturnid";

    // Cookies not accept exception string
    public static final String COOKIES_NOT_ACCEPTED = "Cookies not accepted";

    // The tracking ID parameter
    private static final String TRACKING_ID_PARAMETER_NAME = "trackid";

    private WebSessionService() {
    }

    public static synchronized WebSessionService getInstance() {
        if (instance == null) {
            instance = new WebSessionService();
        }

        return instance;
    }

    /**
     * Obtains the Session in the servlet environment.
     *
     * @param request  The servlet request
     * @param response The servlet response
     * @return JGSession
     * @throws SessionException on failure to retrieve session
     * @throws CookieException  when cookies aren't accepted.
     */
    public JGSession getSession(HttpServletRequest request, HttpServletResponse response)
            throws SessionException, CookieException {
        System.out.println("WebSessionService:getSession");
        // If JGSession has already been put on the request then return that
        JGSession jgSession = (JGSession) request.getAttribute(SESSION_ATTRIBUTE_NAME);

        if (jgSession != null) {
            jgLog.debug("Returning existing JGSession from request");
            //Cart cart = CartSessionManager.getInstance().getCart(request.getSession());
            //jgLog.debug(cart.getNumItems() + " items in Cart");
            //jgLog.trace("cart in session? " + cart.toXmlString());
            return jgSession;
        }

        System.out.println("jgSession " + jgSession);
        // Get the cookie and call the session factory to get the session.
        String cookieID = getCookieID(request);

        if (cookieID.equals("")) {
            // Set a new cookie
            cookieID = setNewCookie(request, response);
        }

        // Get the session for the cookie
        jgSession = SessionManager.getInstance().getSession(cookieID);

        System.out.println("jgSession " + jgSession);
        if (jgSession == null) {
            // if it's not found, this is a new session
            jgSession = SessionManager.getInstance().newSession(cookieID);
            jgLog.debug("Returning new JGSession from DB");
        } else {
            // Check for expired session
            jgLog.debug("Checking JGSession activity at " + Calendar.getInstance().getTime());
            if (jgSession.isExpired()) {
                //logoutUser(request, response, jgSession);
            } else if (jgSession.hasDonorId() && !jgSession.isActive()) {
                //Cart cart = CartSessionManager.getInstance().getCart(request.getSession());
                //jgLog.debug(cart.getNumItems() + " items in Cart");
                //jgLog.trace("cart in session? " + cart.toXmlString());
                SessionManager.getInstance().setUserInactive(jgSession);
            }
            jgLog.debug("Returning existing JGSession from DB");
        }

        System.out.println("jgSession " + jgSession);
        setJGSessionInHttpSession(request, jgSession);

        // Set "affiliate" and "user" attributes, if applicable
        setUserInSession(request, jgSession);

        //Cart cart = CartSessionManager.getInstance().getCart(request.getSession());
        //jgLog.debug(cart.getNumItems() + " items in Cart");
        //jgLog.trace("cart in session? " + cart.toXmlString());
        System.out.println("jgSession " + jgSession);

        return jgSession;
    }

    private void setJGSessionInHttpSession(HttpServletRequest request, JGSession jgSession) {
        // Put the JGSession on HttpSession
        HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute(SESSION_ATTRIBUTE_NAME, jgSession);

        // Put the JGSession in HttpRequest
        request.setAttribute(SESSION_ATTRIBUTE_NAME, jgSession);
    }

    /**
     * Forces a new session in the servlet environment, deleting any old session.
     *
     * @param request  The servlet request
     * @param response The servlet response
     * @return JGSession
     * @throws SessionException on failure to create new session
     * @throws CookieException  when cookies aren't accepted.
     */
    public JGSession getNewSession(HttpServletRequest request, HttpServletResponse response)
            throws SessionException, CookieException {
        // Get the current session
        JGSession jgSession = getSession(request, response);

        // Clear the session
        //logoutUser(request, response, jgSession);

        // Get a new session
        jgSession = SessionManager.getInstance().newSession(jgSession.getJSessionId());

        return jgSession;
    }

    /**
     * Obtains the cookie value from the request.
     *
     * @param request The servlet request
     * @return cookieID
     */
    private String getCookieID(HttpServletRequest request) {
        String cookieID = "";

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(SESSION_COOKIE_NAME)) {
                    cookieID = cookie.getValue();
                    break;
                }
            }
        }

        return cookieID;
    }

    /**
     * Sets a new cookie value on the response.
     *
     * @param request  The servlet request
     * @param response The servlet response
     * @return String
     */
    private String setNewCookie(HttpServletRequest request, HttpServletResponse response) {
        HttpSession httpSession = request.getSession(true);

        String cookieID = httpSession.getId();

        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, cookieID);
        cookie.setPath("/");
        cookie.setMaxAge(47304000);
        response.addCookie(cookie);

        return cookieID;
    }

    /**
     * Persists the jg session and the session's attributes.
     *
     * @param request servlet request
     * @throws SessionException on serialization failure
     */
    public void saveSession(HttpServletRequest request) throws SessionException {

    }

    // Private method that gets the session attributes from the JGSession,
    // and puts them on HttpSession.
    //
    // Then checks login status and puts the Donor and Affiliate on the
    // HttpSession, as applicable.
    private void setAttributesInHttpSession(HttpServletRequest request, JGSession jgSession) throws SessionException {
        if (jgSession == null) {
            throw new SessionException("Session is Null");
        }

        HttpSession httpSession = request.getSession(true);

        //for (JGSessionAttribute sessionAttribute : jgSession.getSessionAttributes()) {
        //    httpSession.setAttribute(sessionAttribute.getAttributeName(), sessionAttribute.getAttribute());
        //}
    }

    // Sets the "user" and "affiliate" session attributes
    private void setUserInSession(HttpServletRequest request, JGSession jgSession) throws SessionException {
        HttpSession httpSession = request.getSession(true);
    }

    private String getUrl(HttpServletRequest request) {
        String url = request.getContextPath() + request.getServletPath();
        String action = StringUtil.nullToString(request.getParameter("acton"));
        String charity = StringUtil.nullToString(request.getParameter("charityId"));
        String category = StringUtil.nullToString(request.getParameter("catId"));

        if (!url.contains("jsp")) {
            url = (action.equals("")) ? url : url + "?" + "acton=" + action;
        } else if (!(category.equals("") && charity.equals(""))) {
            url += "?";
            url = (category.equals("")) ? url : url + "catId=" + category + "&";
            url = (charity.equals("")) ? url : url + "charId=" + charity + "&";
        }

        return url;
    }
}
