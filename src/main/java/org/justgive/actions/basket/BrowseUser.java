package org.justgive.actions.basket;

import org.justgive.action.ActionException;
import org.justgive.action.ActionURL;
import org.justgive.action.BaseAction;
import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;
import org.justgive.model.Donor;
import org.justgive.services.UserManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BrowseUser extends BaseAction {
    private static Logger jgLog = LoggerFactory.getLogger(BrowseUser.class);
    private UserManager userManager;

    public BrowseUser() {
        this.userManager = UserManager.getInstance();
    }

    @Override
    protected ActionURL execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        String userIdStr = (String) request.getParameter("userId");
        System.out.println("browseUser: userIdStr " + userIdStr);

        long userId = Long.parseLong(userIdStr);
        Donor donor = userManager.findOne(userId);

        request.setAttribute("donor", donor);

        return new ActionURL("view.user.user");
    }
}
