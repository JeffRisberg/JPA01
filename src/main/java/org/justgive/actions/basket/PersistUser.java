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
        Donor donor;

        if (userIdStr != null && userIdStr.length() > 0) {
            long userId = Long.parseLong(userIdStr);
            donor = userManager.findOne(userId);
        } else {
            donor = new Donor();
        }

        donor.setFirstName(firstName);
        donor.setLastName(lastName);
        donor.setLogin(email);

        if (donor.getId() != null) {
            userManager.update(donor); // existing donor
        } else {
            userManager.save(donor); // new donor
        }

        return new BrowseUsers().execute(request, response);
    }
}
