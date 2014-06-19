package org.justgive.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * User: peter
 * Date: 4/1/13
 * Time: 12:26 PM
 */
public abstract class ActionHandler {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    protected ActionHandler(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        this.request = request;
        this.response = response;
        this.session = session;
    }

    public abstract ActionURL handleAction() throws ActionException;
}
