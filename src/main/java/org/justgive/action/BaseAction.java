package org.justgive.action;

import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseAction
        implements Action {
    private static Logger jgLog = LoggerFactory.getLogger(BaseAction.class);

    public BaseAction() {
    }

    public ActionURL handleRequest(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        // Common request code here (e.g., logging, etc.)

        return execute(request, response);
    }

    protected abstract ActionURL execute(HttpServletRequest request, HttpServletResponse response)
            throws ActionException;
}
