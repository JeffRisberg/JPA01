package org.justgive.session;

import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * Donor: bdan
 * Date: 2013.01.07
 * Time: 14:59
 * To change this template use File | Settings | File Templates.
 */
public class SessionLocale {
    private static final Logger jgLog = LoggerFactory.getLogger(SessionLocale.class);

    protected ServletRequest request;
    protected HttpSession session;

    public SessionLocale(HttpServletRequest request) {
        this.request = request;
        this.session = request.getSession();
    }

    public SessionLocale(ServletRequest request, HttpSession session) {
        this.request = request;
        this.session = session;
    }

    public Locale getLocale() {
        Locale locale = (Locale) session.getAttribute("locale");
        if (request.getParameter("language") != null && request.getParameter("country") != null) {
            locale = new Locale(request.getParameter("language"),request.getParameter("country"));
            session.setAttribute("locale", locale);
        }
        else if (locale == null) {
            locale = request.getLocale();
            session.setAttribute("locale", locale);
        }

        return locale;
    }
}
