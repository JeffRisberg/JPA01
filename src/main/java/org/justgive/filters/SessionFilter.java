package org.justgive.filters;

import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Derived from code written by Curtis in 2011.
 */
public class SessionFilter implements Filter {
    private static Logger jgLog = LoggerFactory.getLogger(SessionFilter.class);

    private FilterConfig filterConfig;

    public void init(FilterConfig config) {
        this.filterConfig = config;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (initSession(request, response)) {
            chain.doFilter(request, response);
            cleanUpSession(request);
        } else {
            displayNoCookiePage(request, response);
        }
    }

    public void destroy() {
    }

    private boolean initSession(ServletRequest request, ServletResponse response)
            throws ServletException {
        //try {
        //    WebSessionService.getInstance().getSession(request, response);
        //} catch (SessionException e) {
        //    jgLog.error("ERROR INITIALIZING SESSION: " + e.getMessage());
        //    throw new ServletException(e.getMessage());
        //} catch (CookieException e) {
        //    return false;
        //}
        return true;
    }

    private void cleanUpSession(ServletRequest request) {
        //try {
        //    WebSessionService.getInstance().saveSession(request);
        //} catch (SessionException e) {
        //    jgLog.error("ERROR SETTING SESSION ATTRIBUTES");
        //}
    }

    private void displayNoCookiePage(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {
        //filterConfig.getServletContext().getRequestDispatcher(ActionURL.NO_COOKIE.getURL()).forward(request, response);
    }
}
