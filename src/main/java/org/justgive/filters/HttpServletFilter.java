package org.justgive.filters;

import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <code>HttpServletFilter</code> should be extended for Filters that should only be invoked on
 * HttpServletRequests
 */
public abstract class HttpServletFilter extends BaseFilter {
    private static Logger jgLog = LoggerFactory.getLogger(HttpServletFilter.class);

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        super.doFilter(request, response, chain);
        if (request instanceof HttpServletRequest) {
            doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
        } else {
            chain.doFilter(request, response);
        }
    }

    public abstract void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException;

    protected void forward(HttpServletRequest request, HttpServletResponse response, String vendorPage)
            throws ServletException {
        try {
            jgLog.debug("forwarding to " + vendorPage + " now...");
            request.getRequestDispatcher(vendorPage).forward(request, response);
        } catch (Exception e) {
            jgLog.debug("error forwarding to " + vendorPage);
            jgLog.error(e);
            throw new ServletException(e.getMessage());
        }
    }
}
