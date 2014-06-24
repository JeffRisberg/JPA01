package org.justgive.actions.basket;

import org.justgive.action.ActionException;
import org.justgive.action.ActionURL;
import org.justgive.action.BaseAction;
import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;
import org.justgive.models.User;
import org.justgive.services.UserManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class BrowseUsers extends BaseAction {
    private static Logger jgLog = LoggerFactory.getLogger(BrowseUsers.class);
    private UserManager userManager;

    public BrowseUsers() {
        this.userManager = UserManager.getInstance();
    }

    @Override
    protected ActionURL execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        System.out.println("browseUsers");

        List<User> users = userManager.findAll();
        request.setAttribute("users", users);

        return new ActionURL("view.user.users");
    }
}
