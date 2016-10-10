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

public class CreateUser extends BaseAction {
    private static Logger jgLog = LoggerFactory.getLogger(CreateUser.class);
    private UserManager userManager;

    public CreateUser() {
        this.userManager = UserManager.getInstance();
    }

    @Override
    protected ActionURL execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {

        Donor donor = new Donor();
        donor.setFirstName("");
        donor.setLastName("");
        donor.setLogin("");
        request.setAttribute("donor", donor);

        return new ActionURL("view.user.form");
    }
}
