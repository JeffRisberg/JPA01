package org.justgive.action;

import org.justgive.actions.user.*;

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
        System.out.println("finding action for: servletPath " + servletPath + ", actionClass " + actionClass);

        if (actionClass.equalsIgnoreCase("browseUsers"))
            return new BrowseUsers();
        if (actionClass.equalsIgnoreCase("browseUser"))
            return new BrowseUser();
        if (actionClass.equalsIgnoreCase("deleteUser"))
            return new DeleteUser();
        if (actionClass.equalsIgnoreCase("editUser"))
            return new EditUser();
        if (actionClass.equalsIgnoreCase("updateUser"))
            return new UpdateUser();

        throw new ActionNotFoundException("");
    }

    public Action getDefaultAction(String servletPath) throws ActionNotFoundException {
        System.out.println("finding default action for: servletPath " + servletPath);

        return new BrowseUsers();
    }
}
