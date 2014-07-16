package org.justgive.filters;

import org.justgive.action.ActionException;
import org.justgive.action.ActionRouter;
import org.justgive.action.ActionURL;
import org.justgive.actions.handlers.UncaughtExceptionHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Provides a universal procedure for fatal exception handling in the webapp environment.
 * <p/>
 * Created by IntelliJ IDEA.
 * User: curtis
 * Date: 3/25/14
 * Time: 4:27 PM
 */
public class HttpRequestFilter extends HttpServletFilter {
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Throwable e) {
            UncaughtExceptionHandler exceptionHandler = new UncaughtExceptionHandler();
            exceptionHandler.uncaughtException(e);
            ActionRouter router = new ActionRouter(request.getServletContext());

            //Vendor vendor = (Vendor) request.getSession().getAttribute("vendor");
            try {
                request.setAttribute("error_message", e.getMessage());
                router.route(ActionURL.ERROR, null, request, response);
            } catch (ActionException e1) {
                e1.printStackTrace();
            }
        }
    }
}
