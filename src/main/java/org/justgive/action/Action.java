package org.justgive.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action interface to execute the code associated with an implementing
 * action.  Actions are executed in the servlet environment, and specific
 * implemented Actions are obtained through the ActionFactory.
 *
 * Because the ActionFactory instantiates only one copy of each implemented
 * Action, the individual Actions are singletons and are not thread-safe.
 */
public interface Action 
{ 
	public ActionURL handleRequest(HttpServletRequest request, HttpServletResponse response) throws ActionException;
}
