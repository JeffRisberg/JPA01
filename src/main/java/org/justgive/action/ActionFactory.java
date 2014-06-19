package org.justgive.action;

import org.justgive.actions.user.BrowseUsers;

/**
 * Subset of the real action factory
 *
 * @author Jeff Risberg
 * @since April 2014
 */
public class ActionFactory {

    static ActionFactory instance = null;

    public static ActionFactory getInstance() {
        if (instance == null) {
            instance = new ActionFactory();
        }
        return instance;
    }

    public Action getAction(String servletPath, String actionClass) throws ActionNotFoundException {
        System.out.println("servletPath " + servletPath);
        System.out.println("actionClass " + actionClass);

        return new BrowseUsers();
    }

    public Action getDefaultAction(String servletPath) throws ActionNotFoundException {
        System.out.println("servletPath " + servletPath);

        return new BrowseUsers();
    }
}
