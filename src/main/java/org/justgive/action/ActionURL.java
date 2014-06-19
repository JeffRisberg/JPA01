package org.justgive.action;

import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;
import org.justgive.properties.JustGiveProperties;
import org.justgive.properties.PropertyException;
import org.justgive.util.RequestParams;
import org.justgive.util.StringUtil;

import javax.servlet.http.HttpServletRequest;

// Action routers are immutable
public class ActionURL {
    private static Logger jgLog = LoggerFactory.getLogger(ActionURL.class);

    public static final ActionURL ERROR = new ActionURL("view.error");
    public static final ActionURL NOT_FOUND = new ActionURL("view.file.not.found");
    public static final ActionURL FORBIDDEN = new ActionURL("view.forbidden");
    public static final ActionURL NO_COOKIE = new ActionURL("view.no.cookie");
    public static final String HTTPS = "https";
    public static final String HTTP = "http";

    protected String actionUrl; //raw url fragment or absolute (ex: '/basket' or 'https://www.justgive.org/basket')
    protected RequestParams requestParams = new RequestParams();
    protected RouteMethod routeMethod = RouteMethod.FORWARD;
    protected boolean isSecure = false;
    protected String contentType = "text/html";

    // reconstruct destination from request params (self-redirect)
    public static ActionURL createActionUrlFromRequest(HttpServletRequest request) {
        ActionURL actionURL = new ActionURL(request.getContextPath() + request.getServletPath());
        System.out.println("actionURL " + actionURL.getURL());
        actionURL.requestParams = new RequestParams(request);
        jgLog.debug("ActionURL : " + actionURL.getURL());
        return actionURL;
    }

    public ActionURL(String url) {
        forward();
        setURL(url);
    }

    /**
     * Sets the url and/or page name based on:
     * 1. if a property with the provided value exists, use that
     * 2. if the String is a valid url, use it.
     * 3. extrapolate jsp name from String, eg: view.thisPage = /view/this_page.jsp
     * <p/>
     * conditionally sets 'isSecure' and 'isRedirect' depending on url value
     *
     * @param view either a view property name or a raw view url
     */
    public void setURL(String view) {
        actionUrl = extractActionUrl(view);
        jgLog.debug("view: " + view + ", actionUrl: " + actionUrl);

        if (urlHasScheme()) {
            if (schemeIsSecure()) makeSecure();
            redirect();
        }
    }

    private String extractActionUrl(String view) {
        String actionUrl = loadViewFromProperties(view);
        if (actionUrl != null) return actionUrl;

        jgLog.trace(view + " not found, checking if valid URL");
        //if property not found, view is either a raw url,
        //or a view property that has not been set in app.properties
        if (isValidUrl(view)) return view;
        return parseUrlFromView(view);
    }

    private String loadViewFromProperties(String view) {
        jgLog.trace("looking for view in properties: " + view);
        String url;
        try {
            url = JustGiveProperties.getString(view);
            //property found for view name, now set view name
            jgLog.trace("found URL " + url);
            return url;
        } catch (PropertyException e) {
            return null;
        }
    }

    private boolean isValidUrl(String url) {
        jgLog.trace("isValidUrl? " + (url.startsWith(HTTP) || url.contains("/")));
        return url.startsWith(HTTP) || url.contains("/");
    }

    /**
     * extrapolate jsp name from property
     * eg: view.thisPage = /view/this_page.jsp
     *
     * @param view
     * @return
     */
    private String parseUrlFromView(String view) {
        jgLog.debug("view: " + view);
        String url = StringUtil.toCamelCase("/" + view.replace('.', '/') + ".jsp", "_");
        jgLog.debug("Generated view page: " + url);
        return url;
    }

    private boolean urlHasScheme() {
        jgLog.trace("urlHasScheme? " + (actionUrl.startsWith(HTTP)));
        return actionUrl.startsWith(HTTP);
    }

    private boolean schemeIsSecure() {
        jgLog.trace("schemeIsSecure? " + (actionUrl.startsWith(HTTPS)));
        return actionUrl.startsWith(HTTPS);
    }

    public String getURL() {
        return (hasParams()) ? actionUrl + getQueryString() : actionUrl;
    }

    public String getAbsoluteURL(HttpServletRequest req, Object vendor) {
        String url;
        //if (urlHasScheme()) {
        url = actionUrl;
        //} else {
        //    String path = new VendorPage(vendor, actionUrl, req).determinePath();
        //    url = getScheme() + "://" + req.getServerName() + path;
        //}
        url = (hasParams()) ? url + getQueryString() : url;
        jgLog.debug("getAbsoluteURL() " + url);
        return url;
    }

    public ActionURL redirect() {
        routeMethod = RouteMethod.REDIRECT;
        return this;
    }

    public ActionURL forward() {
        routeMethod = RouteMethod.FORWARD;
        return this;
    }

    public ActionURL include() {
        routeMethod = RouteMethod.INCLUDE;
        return this;
    }

    public ActionURL makeSecure() {
        isSecure = true;
        if (!schemeIsSecure()) redirect();
        return this;
    }

    public boolean isRedirect() {
        return (routeMethod.equals(RouteMethod.REDIRECT));
    }

    public boolean isForward() {
        return (routeMethod.equals(RouteMethod.FORWARD));
    }

    public boolean isInclude() {
        return (routeMethod.equals(RouteMethod.INCLUDE));
    }

    public boolean isSecure() {
        return isSecure;
    }

    public String getContentType() {
        return contentType;
    }

    public String getParams() {
        return requestParams.toString();
    }

    public void addQueryParam(String key, String val) {
        requestParams.setParam(key, val);
    }

    public boolean hasParams() {
        return !requestParams.isEmpty();
    }

    public String getQueryString() {
        if (actionUrl.contains("?")) {
            return "&" + requestParams.toString();
        } else {
            return "?" + requestParams.toString();
        }
    }

    protected String getScheme() {
        return isSecure() ? HTTPS : HTTP;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ActionURL)) return false;
        ActionURL other = (ActionURL) obj;
        jgLog.debug("actionUrl: " + actionUrl);
        jgLog.debug("obj.actionUrl: " + other.actionUrl);
        jgLog.debug("actionUrl.equals(other.actionUrl)? " + actionUrl.equals(other.actionUrl));
        jgLog.debug("actionParams: " + requestParams);
        jgLog.debug("obj.actionParams: " + other.requestParams);
        jgLog.debug("actionParams.toString().equals(other.actionParams.toString())? " + requestParams.toString().equals(
                other.requestParams.toString()));
        if (!actionUrl.equals(other.actionUrl)) return false;
        if (!requestParams.toString().equals(other.requestParams.toString())) return false;
        return true;
    }

    public String toString() {
        return "ActionURL[" + actionUrl + (isSecure ? "(s)" : "") + "]";
    }
}
