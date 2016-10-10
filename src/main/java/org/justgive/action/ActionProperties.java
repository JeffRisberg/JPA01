package org.justgive.action;

import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;
import org.justgive.properties.JustGiveProperties;

/**
 * Created by IntelliJ IDEA.
 * Donor: peter
 * Date: 5/15/13
 * Time: 3:37 PM
 */

public class ActionProperties {
    private static Logger jgLog = LoggerFactory.getLogger(ActionProperties.class);

    public ActionProperties() {}

    public String getValue(String servletPath, String actionName) {
        String actionPropertyName = constructPropertyName(servletPath, actionName);
        return loadProperty(actionPropertyName);
    }

    public boolean contains(String servletPath, String actionName) {
        return getValue(servletPath, actionName) != null;
    }

    private String constructPropertyName(String servletPath, String actionName) {
        String actionPropertyName = "action." + servletPath + "." + actionName.toLowerCase();
        jgLog.trace("Action Property Name: " + actionPropertyName);
        return actionPropertyName;
    }

    private String loadProperty(String actionPropertyName) {
        String actionProperty = JustGiveProperties.getStringOrNull(actionPropertyName);
        jgLog.trace("Action Property Value: " + actionProperty);
        return actionProperty;
    }
}
