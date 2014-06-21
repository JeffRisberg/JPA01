package org.justgive.action;

import org.justgive.controllers.Controller;
import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;
import org.justgive.util.StringUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <!--
 * Copyright 2002 JustGive.org, 312 Sutter st, Suite 410,
 * San Francisco, Ca, 94108, U.S.A.  All Rights Reserved.
 * <p/>
 * This software is the confidential and proprietary information of
 * JustGive.org ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with JustGive.
 * <p/>
 * CopyrightVersion 1.0
 * -->
 * <p/>
 * <pre>
 *
 * Takes an ActionURL, or a String to be converted into an ActionURL, and
 * provides mechanism to route the request based on the properties of that
 * object.
 *
 * Action routers are immutable.
 *
 * @author Peter Cowan
 *         </pre>
 */
public class ActionRouter {
    private static Logger jgLog = LoggerFactory.getLogger(ActionRouter.class);

    private final ServletContext servletContext;

    public ActionRouter(Controller servlet) {
        this(servlet.getServletContext());
    }

    public ActionRouter(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public synchronized void route(String url, HttpServletRequest request, HttpServletResponse response)
            throws ActionException {
        route(url, null, request, response);
    }

    public synchronized void route(String url, Object vendor, HttpServletRequest request, HttpServletResponse response)
            throws ActionException {
        route(new ActionURL(url), vendor, request, response);
    }

    public synchronized void route(ActionURL url, HttpServletRequest request,
                                   HttpServletResponse response) throws ActionException {
        route(url, null, request, response);
    }

    /**
     * <pre>
     * Route the request based on the properties described in the ActionURL,
     * ex: redirect, include or forward. If a Vendor is present in the
     *
     * HttpSession, the page routed too may be different than the specified URL.
     *
     * ex: for Vendor "vendor", a request pointing to "/donate.jsp", may get
     * re-routed to "/vendor/donate.jsp", if that path exists in app.properties
     * with a boolean value of true.
     * </pre>
     *
     * @param url
     * @param vendor
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ActionException     thrown if there is a problem routing the request
     * @throws java.io.IOException thrown if there is a problem routing the request
     */
    public synchronized void route(ActionURL url, Object vendor, HttpServletRequest request,
                                   HttpServletResponse response) throws ActionException {
        if (StringUtil.isEmpty(url.getURL())) {
            return;
        } else if (url.isRedirect()) {
            redirect(url, vendor, request, response);
        } else {
            dispatch(url, vendor, request, response);
        }
    }

    private void redirect(ActionURL url, Object vendor, HttpServletRequest request, HttpServletResponse response)
            throws ActionException {
        try {
            String redirectUrl = url.getAbsoluteURL(request, vendor);
            jgLog.debug("redirectUrl: " + redirectUrl);
            response.sendRedirect(redirectUrl);
        } catch (Exception e) {
            jgLog.error(e.getMessage());
            throw new ActionException(e.getMessage());
        }
    }

    private void dispatch(ActionURL url, Object vendor, HttpServletRequest request, HttpServletResponse response)
            throws ActionException {
        try {
            response.setContentType(url.getContentType());

            String dispatchPath = getDispatchPath(url, vendor, request);
            RequestDispatcher dispatcher = servletContext.getRequestDispatcher(dispatchPath);

            if (url.isInclude()) {
                dispatcher.include(request, response);
            } else {
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            jgLog.error(e);
            throw new ActionException(e.getMessage());
        }
    }

    private String getDispatchPath(ActionURL url, Object vendor, HttpServletRequest request) {
        //VendorPage vendorPage = new VendorPage(vendor, url.getURL(), request);
        String dispatchPath;
        //if (vendorPage.isJsp()) {
        //    dispatchPath = vendorPage.determinePath();
        //} else {
        dispatchPath = url.getURL();
        //}
        jgLog.debug("dispatchPath: " + dispatchPath);
        return dispatchPath;
    }
}

