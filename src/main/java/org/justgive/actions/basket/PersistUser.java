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

public class PersistUser extends BaseAction {
    private static Logger jgLog = LoggerFactory.getLogger(PersistUser.class);
    private UserManager userManager;

    public PersistUser() {
        this.userManager = UserManager.getInstance();
    }

    @Override
    protected ActionURL execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        String userIdStr = (String) request.getParameter("userId");
        System.out.println("updateUser:  userIdStr " + userIdStr);

        String firstName = (String) request.getParameter("firstName");
        String lastName = (String) request.getParameter("lastName");
        String email = (String) request.getParameter("email");
        User user;

        if (userIdStr != null && userIdStr.length() > 0) {
            long userId = Long.parseLong(userIdStr);
            user = userManager.findOne(userId);
        } else {
            user = new User();
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        if (user.getId() != null) {
            userManager.update(user); // existing user
        } else {
            userManager.save(user); // new user
        }

        return new BrowseUsers().execute(request, response);
    }
}
