package org.justgive.actions.charity;

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

public class BrowseCharities extends BaseAction {
    private static Logger jgLog = LoggerFactory.getLogger(BrowseCharities.class);
    private UserManager userManager;

    public BrowseCharities() {
        this.userManager = UserManager.getInstance();
    }

    @Override
    protected ActionURL execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        List<User> users = userManager.findAll();
        request.setAttribute("users", users);

        return new ActionURL("view.simple.browse");
    }
}
