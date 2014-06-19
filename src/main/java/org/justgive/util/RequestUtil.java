package org.justgive.util;

import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: peter c
 * Date: Feb 11, 2008
 * Time: 12:35:13 PM
 */
@SuppressWarnings({"deprecation"})
public class RequestUtil {
    private static Logger jgLog = LoggerFactory.getLogger(RequestUtil.class);
    public static final String BACK_ATTRIBUTE = "back";

    /**
     * Check that the submit param is not empty, or the image input param has x/y coordinates
     *
     * @param request
     * @param submitName name of the submit button
     * @return
     */
    public static boolean isSubmitted(HttpServletRequest request, String submitName) {
        return (!StringUtil.isEmpty(request.getParameter(submitName)) || RequestUtil.isImageButtonClicked(request,
                submitName));
    }

    public static boolean isImageButtonClicked(HttpServletRequest request, String buttonName) {
        return request.getParameter(buttonName + ".x") != null || request.getParameter(buttonName + ".y") != null;
    }

    /**
     * Calls isSubmitted(HttpServletRequest request, String submitName, String imageName)
     * with the default names "submit" and "image"
     *
     * @param request
     * @return
     */
    public static boolean isSubmitted(HttpServletRequest request) {
        return isSubmitted(request, "submit") || isSubmitted(request, "image");
    }

    public static boolean isMultipartRequest(HttpServletRequest request) {
        if (!"post".equals(request.getMethod().toLowerCase())) {
            return false;
        }
        String contentType = request.getContentType();

        if (contentType == null) {
            return false;
        } else if (contentType.toLowerCase().startsWith("multipart/")) {
            return true;
        }
        return false;
    }

    /**
     * @return
     * @deprecated see RequestParams
     */
    public static String reconfigureQueryString(HttpServletRequest request) {
        return new RequestParams(request).toString();
    }

    /**
     * @return
     * @deprecated see RequestParams
     */
    public static String reconfigureQueryString(HttpServletRequest request, String param, String value) {
        return new RequestParams(request).setParam(param, value).toString();
    }


    public static String extractActionPath(HttpServletRequest request) {
        String requestPath = request.getRequestURI();
        String contextPath = request.getContextPath();
        String servletPath = getServletPath(request);
        String basePath = "/" + contextPath + servletPath + "/";
        String actionPath = requestPath.replaceFirst(basePath, "");
        jgLog.debug("actionPath: " + actionPath);
        return actionPath;
    }

    public static String getServletPath(HttpServletRequest request) {
        return request.getServletPath().substring(1);
    }
}
