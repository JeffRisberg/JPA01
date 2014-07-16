package org.justgive.filters;

import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: peter c
 * Date: Mar 31, 2009
 * Time: 11:38:39 AM
 * <p/>
 * A filter to set a Vendor object in the HttpSession based on either
 * 1) an explicitly set request parameters, or
 * 2) the subdomain of the current virtual host
 */
public class VendorFilter extends HttpServletFilter {
    private static Logger jgLog = LoggerFactory.getLogger(VendorFilter.class);

    //private VendorSessionManager vendorSessionManager;
    //private VendorManager vendorManager;

    public void init(FilterConfig config) {
        super.init(config);
        //vendorSessionManager = VendorSessionManager.getInstance();
        //vendorManager = VendorManager.getInstance();
    }

    /**
     * Checks the session for a Vendor object, if the object does not exist,
     * or the existing Vendor location is not equal to the location in the
     * subdomain, a new Vendor is loaded and set in the HttpSession.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResposne
     * @param chain    FilterChain
     * @throws java.io.IOException            thrown by chain.doFilter
     * @throws javax.servlet.ServletException thrown by chain.doFilter
     */
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        //setVendorSessionBySubdomain(request);

        chain.doFilter(request, response);
    }
}
