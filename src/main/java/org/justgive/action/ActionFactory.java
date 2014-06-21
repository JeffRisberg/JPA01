package org.justgive.action;

import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;
import org.justgive.util.StringUtil;

import java.util.Hashtable;

public class ActionFactory {
    private static Logger jgLog = LoggerFactory.getLogger(ActionFactory.class);

    private static ActionFactory instance = null;
    private Hashtable<Class<? extends Action>, Action> actions = new Hashtable<Class<? extends Action>, Action>();

    private ActionFactory() {
    }

    public static synchronized ActionFactory getInstance() {
        if (instance == null) {
            instance = new ActionFactory();
        }

        return instance;
    }

    public synchronized Action getAction(Class<? extends Action> classType) {
        Action action = actions.get(classType);

        if (action == null) {
            action = new ActionLoader(classType).loadAction();
            actions.put(classType, action);
        }
        return action;
    }

    /**
     * Returns an {@link Action} using the given servlet path,
     * and the given argument "action".
     *
     * @param servletPath The path of servlet this action is associated with. eg "basket"
     * @param actionName  The mapped action name
     * @return The instance of the Action class
     * @throws ActionNotFoundException if there is a problem instantiating the Action
     */
    public Action getAction(String servletPath, String actionName) throws ActionNotFoundException {
        jgLog.debug("Loading ServletPath: " + servletPath + ", ActionName: " + actionName);
        //actionName may not be null, but ActionNotFoundException must be thrown because callers
        //may be expecting it, as it would have been thrown from getAction(String classname).
        if (StringUtil.isEmpty(actionName)) throw new ActionNotFoundException("NullAction Not Valid.");

        Action action = getActionFromProperties(servletPath, actionName);
        if (action == null) return getAction(constructActionClassName(servletPath, actionName));
        return action;
    }

    private Action getActionFromProperties(String servletPath, String actionName) throws ActionNotFoundException {
        ActionProperties actionProperties = new ActionProperties();
        if (!actionProperties.contains(servletPath, actionName)) return null;

        String actionPropertyValue = actionProperties.getValue(servletPath, actionName);
        if (isFullActionClassName(actionPropertyValue)) {
            return getAction(actionPropertyValue);
        } else {
            return getAction(constructActionClassName(servletPath, actionPropertyValue));
        }
    }

    private boolean isFullActionClassName(String actionName) {
        return (actionName != null && actionName.startsWith("org.justgive.actions."));
    }

    private String constructActionClassName(String servletPath, String actionName) {
        String actionClassName = "org.justgive.actions." + servletPath + "." + actionName;
        jgLog.debug("ClassName : " + actionClassName);
        return actionClassName;
    }

    private Action getAction(String classname) throws ActionNotFoundException {
        return getAction(classNameToClass(classname));
    }

    private Class<? extends Action> classNameToClass(String classname) throws ActionNotFoundException {
        ClassLoader loader = this.getClass().getClassLoader();
        try {
            return (Class<? extends Action>) loader.loadClass(classname);
        } catch (ClassNotFoundException e) {
            jgLog.info(classname + " not found");
            throw new ActionNotFoundException(e.getMessage());
        }
    }

    /**
     * Returns the default {@link Action} using the given servlet path.
     *
     * @param servletPath The path of servlet this action is associated with. eg "basket"
     * @return The instance of the Action class
     * @throws ActionNotFoundException if there is a problem instantiating the Action
     */
    public Action getDefaultAction(String servletPath) throws ActionNotFoundException {
        jgLog.debug("Loading default Action");
        System.out.println("loading default Action for " + servletPath);

        String defaultActionName = "undefined_action";
        Action action = getActionFromProperties(servletPath, defaultActionName);

        if (action != null) return action;

        String classname = constructActionClassName(servletPath, "UndefinedAction");
        return getAction(classname);
    }
}
