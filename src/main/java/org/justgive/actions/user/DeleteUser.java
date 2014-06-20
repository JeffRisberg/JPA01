package org.justgive.actions.user;

import org.justgive.action.ActionException;
import org.justgive.action.ActionURL;
import org.justgive.action.BaseAction;
import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;
import org.justgive.models.User;
import org.justgive.services.UserManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUser extends BaseAction {
    private static Logger jgLog = LoggerFactory.getLogger(DeleteUser.class);
    private UserManager userManager;

    public DeleteUser() {
        this.userManager = UserManager.getInstance();
    }

    @Override
    protected ActionURL execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        String userIdStr = (String) request.getParameter("userId");
        System.out.println("userIdStr " + userIdStr);

        long userId = Long.parseLong(userIdStr);
        User user = userManager.findOne(userId);

        request.setAttribute("user", user);

        return new ActionURL("view.user");
    }
}
