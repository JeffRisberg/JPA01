package org.justgive.controllers;

import org.justgive.action.Action;
import org.justgive.action.ActionException;
import org.justgive.action.ActionNotFoundException;
import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;
import org.justgive.util.RequestUtil;
import org.justgive.util.StringUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * <code>PathController</code> routes ServletRequests to Actions by parsing the
 * request URI:
 *
 * <pre>
 *
 * To map a URI to an action class, start with the name of the servlet. We will use "simple" as our example, ex:
 * justgive.org/simple. This URI maps to a package under actions in the basket repository: org.justgive.actions.simple.
 * In this package there is a class call "Donate", to point to this action via the webapp, all you have to do is link to
 * justgive.org/simple/donate. No configuration is required.
 *
 * If your action class contains uppercase characters, then the url should use an underscore to mark the beginning of
 * each word besides the first. So, for example, /simple/gift_certificate maps to org.justgive.actions.simple
 * .GiftCertificate.
 *
 * If an action that corresponds directly to the url is not found, next PathController appends "Action" to the end of
 * the class name and tries again: org.justgive.actions.simple.GiftCertificateAction.
 *
 * If that action is not found, it attempts to load org.justgive.actions.simple.UndefinedAction.
 *
 * Finally, if an action is still not found, "null" is returned instead and the request handled by
 * Controller.service()
 *
 * You also have the option of overriding the default mapping with properties in app.properties.
 * If you want your url to be "/simple/gc", then you would just add the property: action.simple.gc=GiftCertificate.
 *
 * Below is the sequence of lookups that PathController follows to try to find an Action class:
 *
 *  1. It looks in app.properties for the property property action.simple.donate. If a Value is found,
 *      it attempts to load an Action with that classname instead of "Donate".
 *  2. First it attempts to load the action org.justgive.actions.simple.Donate
 *  3. Next it attempts to load the action org.justgive.actions.simple.DonateAction
 *  4. Next it looks for the app.properties property action.simple.undefined_action
 *  5. Next it attempts to load the action org.justgive.actions.simple.UndefinedAction
 *
 *  At this point the method returns null, and is handled by Controller, which will forward
 *  to the ActionURL("content.simple.donate").
 *
 * </pre>
 */
public class PathController extends ActionController {
    private static Logger jgLog = LoggerFactory.getLogger(PathController.class);

    @Override
    protected Action getAction(HttpServletRequest request) {
        return lookForAction(RequestUtil.getServletPath(request), RequestUtil.extractActionPath(request));
    }

    protected Action lookForAction(String servletPath, String actionPath) {
        jgLog.debug("servletPath: " + servletPath + "\nactionPath: " + actionPath);
        Action action = loadAction(servletPath, convertPathToClassName(actionPath));
        if (action == null) action = getDefaultAction(servletPath);
        return action;
    }

    protected Action loadAction(String servletPath, String actionName) {
        try {
            return actionFactory.getAction(servletPath, actionName);
        } catch (ActionException e) {
            try {
                return actionFactory.getAction(servletPath, actionName + "Action");
            } catch (ActionNotFoundException e1) {
                jgLog.info(e.getMessage());
                return null;
            }
        }
    }

    protected String convertPathToClassName(String action) {
        return StringUtil.initCaps(StringUtil.toCamelCase(action, "_"));
    }

}
