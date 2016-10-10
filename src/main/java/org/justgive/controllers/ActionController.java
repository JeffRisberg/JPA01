package org.justgive.controllers;

import org.justgive.action.Action;
import org.justgive.action.ActionException;
import org.justgive.action.ActionNotFoundException;
import org.justgive.action.ActionURL;
import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;
import org.justgive.util.RequestUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @uthor Jeff Risberg
 * @since April 2014
 */
public class ActionController extends Controller {
    private static Logger jgLog = LoggerFactory.getLogger(ActionController.class);

    protected void delegate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        Action action = getAction(request);

        ActionURL view = executeAction(action, request, response);
        routeActionView(view, request, response);
    }

    protected Action getAction(HttpServletRequest request) {
        debugRequestUri(request);
        try {
            String servletPath = RequestUtil.getServletPath(request);
            String actionClass = request.getParameter("acton");

            return actionFactory.getAction(servletPath, actionClass);
        } catch (ActionNotFoundException e) {
            jgLog.info(e.getMessage());
            return getDefaultAction(RequestUtil.getServletPath(request));
        }
    }

    protected Action getDefaultAction(String servletPath) {
        try {
            return actionFactory.getDefaultAction(servletPath);
        } catch (ActionNotFoundException e1) {
            jgLog.info(e1.getMessage());
            return null;
        }
    }

    protected ActionURL executeAction(Action action, HttpServletRequest request, HttpServletResponse response) {
        ActionURL url;
        try {
            return action.handleRequest(request, response);
        } catch (ActionException ex) {
            jgLog.warn(ex.getMessage());
            return getServerErrorView(request, ex.getMessage());
        }
    }

    private void debugRequestUri(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        String requestPath = request.getRequestURI();
        String contextPath = request.getContextPath();
        String servletPath = RequestUtil.getServletPath(request);
        String pathInfo = request.getPathInfo();

        jgLog.trace("requestUrl : " + requestURL);
        jgLog.trace("requestPath: " + requestPath);
        jgLog.trace("contextPath : " + contextPath);
        jgLog.trace("servletPath : " + servletPath);
        jgLog.trace("pathInfo : " + pathInfo);
    }
}
