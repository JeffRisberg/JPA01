package org.justgive.filters;

import org.justgive.action.ActionURL;
import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;
import org.justgive.session.CookieException;
import org.justgive.session.SessionException;
import org.justgive.session.WebSessionService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Web application filter that opens and closes a database transaction.
 * <p/>
 * Map this filter to all requests in web.xml that require session handling.
 * <p/>
 * User: curtis
 * Date: Feb 8, 2011
 * Time: 10:54:36 AM
 */
public class SessionFilter extends HttpServletFilter {
    private static Logger jgLog = LoggerFactory.getLogger(SessionFilter.class);

    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (initSession(request, response)) {
            chain.doFilter(request, response);
            cleanUpSession(request);
        } else {
            displayNoCookiePage(request, response);
        }
    }

    private boolean initSession(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            WebSessionService.getInstance().getSession(request, response);
        } catch (SessionException e) {
            System.out.println("error initializing session");
            jgLog.error("ERROR INITIALIZING SESSION: " + e.getMessage());
            throw new ServletException(e.getMessage());
        } catch (CookieException e) {
            return false;
        }
        return true;
    }

    private void cleanUpSession(HttpServletRequest request) {
        try {
            WebSessionService.getInstance().saveSession(request);
        } catch (SessionException e) {
            jgLog.error("ERROR SETTING SESSION ATTRIBUTES");
        }
    }

    private void displayNoCookiePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        filterConfig.getServletContext().getRequestDispatcher(ActionURL.NO_COOKIE.getURL()).forward(request, response);
    }
}
