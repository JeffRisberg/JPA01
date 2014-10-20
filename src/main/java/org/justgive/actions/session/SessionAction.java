package org.justgive.actions.session;

import org.justgive.action.ActionException;
import org.justgive.action.ActionURL;
import org.justgive.action.BaseAction;
import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;
import org.justgive.models.Charity;
import org.justgive.models.JGSession;
import org.justgive.services.CharityManager;
import org.justgive.session.SessionManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Dump a set of sessions
 *
 * @author Jeff Risberg
 * @since September 2014
 */
public class SessionAction extends BaseAction {
    private static Logger jgLog = LoggerFactory.getLogger(SessionAction.class);
    private SessionManager sessionManager;

    public SessionAction() {
        this.sessionManager = SessionManager.getInstance();
    }

    @Override
    protected ActionURL execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        List<JGSession> sessions = sessionManager.findAll();
        request.setAttribute("sessions", sessions);

        return new ActionURL("view.session.sessions");
    }
}
