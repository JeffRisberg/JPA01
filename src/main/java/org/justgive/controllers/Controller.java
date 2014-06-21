package org.justgive.controllers;

import org.justgive.action.ActionException;
import org.justgive.action.ActionFactory;
import org.justgive.action.ActionRouter;
import org.justgive.action.ActionURL;
import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;
import org.justgive.properties.JustGiveProperties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller class represents the superclass of all JustGive Controller servlets.
 */
public abstract class Controller extends HttpServlet {
    private static Logger jgLog = LoggerFactory.getLogger(Controller.class);

    protected ActionFactory actionFactory;

    /**
     * Initializes the servlet when it is loaded by the servlet engine.
     *
     * @param config the configuration as <code>ServletConfig</code>
     * @throws javax.servlet.ServletException if initialization fails.
     */
    public void init(ServletConfig config) throws ServletException {
        System.out.println("Controller: init");

        try {
            jgLog.debug(getClass().getName() + " - init started");
            super.init(config);
            JustGiveProperties.addPropertiesFile("config/app");
            jgLog.debug(getClass().getName() + " - init complete");
        } catch (Exception e) {
            jgLog.error("Caught Exception loading Servlet: " + e.getMessage());
            jgLog.error(e);
            throw new ServletException(e);
        }

        actionFactory = ActionFactory.getInstance();
    }

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Controller: service " + request.getRequestURL());

        HttpServletRequest customRequest = customizeRequest(request);

        delegate(customRequest, response);
    }

    private HttpServletRequest customizeRequest(HttpServletRequest request) {
        //if (RequestUtil.isMultipartRequest(request)) {
        //    try {
        //        jgLog.debug("Creating MultipartServletRequestWrapper from request");
        //        return RequestUtil.getMultipartRequest(request);
        //    } catch (Exception e) {
        //        jgLog.warn(e);
        //    }
        //}
        return request;
    }

    protected abstract void delegate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException;

    protected ActionURL getServerErrorView(HttpServletRequest request, String errorMessage) {
        jgLog.error("Servlet Error");
        jgLog.error(errorMessage);
        request.setAttribute("server_error", errorMessage);
        return ActionURL.ERROR;
    }

    protected void routeActionView(ActionURL url, HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        //Vendor vendor = (Vendor) request.getSession().getAttribute("vendor");
        Object vendor = null;
        ActionRouter router = new ActionRouter(this);
        try {
            router.route(url, vendor, request, response);
        } catch (ActionException e) {
            throw new ServletException(e.getMessage());
        }
    }
}
