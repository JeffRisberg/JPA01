package org.justgive.actions.charity;

import org.justgive.action.ActionException;
import org.justgive.action.ActionURL;
import org.justgive.action.BaseAction;
import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;
import org.justgive.model.Charity;
import org.justgive.services.CharityException;
import org.justgive.services.CharityManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CharityAction extends BaseAction {
    private static Logger jgLog = LoggerFactory.getLogger(CharityAction.class);
    private CharityManager charityManager;

    public CharityAction() {
        this.charityManager = CharityManager.getInstance();
    }

    @Override
    protected ActionURL execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        try {
            List<Charity> charities = charityManager.getAllCharities(0, 100);
            request.setAttribute("charities", charities);

            return new ActionURL("view.charity.charities");
        } catch (CharityException e) {
            throw new ActionException(e);
        }
    }
}
