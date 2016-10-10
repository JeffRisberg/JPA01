package org.justgive.config;

import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;
import org.justgive.properties.JustGiveProperties;
import org.justgive.properties.PropertyException;
import org.justgive.properties.PropertyFileNotFoundException;
import org.justgive.util.StringUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Environment {
    private static Logger jgLog = LoggerFactory.getLogger(Environment.class);

    // The application's location
    private static String location = "";
    private static Boolean overrideProcessor = null;
    private static Boolean overridePointsProcessor = null;
    private static Boolean overridePointsActivity = null;
    private static String hostname;

    public static String getLocation() {
        return location;
    }

    private static void setLocation(String webappRootDir) {
        location = webappRootDir;
        jgLog.debug("servlet location: " + location);
    }

    public static void initialize() {
        initialize(null);
    }

    /**
     * Loads default host properties and sets hostname for class
     * based on application's webappRootDir
     *
     * @param webappRootDir The application's webappRootDir
     */
    public static void initialize(String webappRootDir) {
        // Set the application's webappRootDir
        if (!StringUtil.isEmpty(webappRootDir)) setLocation(webappRootDir);

        if (loadHostnameProperties() || setDefaultHostName(webappRootDir)) {
            // Load optional host properties file based on the value found in hostname.properties
            if (!loadHostProperties()) jgLog.debug("unable to set hostname");
        }

    }

    private static boolean loadHostnameProperties() {
        try {
            // Load optional hostname properties file to override defaults
            jgLog.debug("loading hostname properties");
            JustGiveProperties.addPropertiesFile("config/hostname");
            jgLog.debug("hostname set from file:" + JustGiveProperties.getHostname());
            return true;
        } catch (PropertyFileNotFoundException e) {
            jgLog.debug(e.getMessage());
            return false;
        }
    }

    private static boolean setDefaultHostName(String webappRootDir) {
        if (webappRootDir == null) return false;
        JustGiveProperties.setHostname(getDefaultHostName());
        jgLog.debug("hostname set from default:" + JustGiveProperties.getHostname());
        return true;
    }

    /**
     * Parses the servlet location, the hostname of localhost
     * and optionally and sandbox prefix to create a default
     * hostname
     *
     * @return hostname as described above
     * @throws org.justgive.properties.PropertyFileNotFoundException if file is not found
     */
    private static String getDefaultHostName() {
        if (location == null) return null;
        // the hostProperties filename is named based on an approximation of the virtual host of the environment the application
        // is being run in. host and app name mappings are found in common/trunk/config/host-mapping.properties this method
        // requires an app server implementation that matches the standardjustgive naming practice of storing the web applications
        // in ${tomcat_home)/webapps. if there are individual sanbox environments, such as devapp2,
        // then the webapps are in ${tomcat_home)/webapps/${sandbox} and the sandbox name is used as an additional element in
        // the default properties file name the basic structure is:
        ///
        // appPrefix + hostPrefix (+ "-" + sandbox)
        // or
        // 'just' + 'dev' (+ '-' + 'peter)
        loadHostMappingProperties();

        String localhost = determineLocalhost();
        String sandbox = determineSandboxName();

        return composeDefaultHostname(localhost, sandbox);
    }

    private static void loadHostMappingProperties() {
        try {
            jgLog.debug("loading host-mapping.properties");
            JustGiveProperties.addPropertiesFile("config/host-mapping.properties");
        } catch (PropertyFileNotFoundException e) {
            // Optional property file
            jgLog.warn(e.getMessage());
        }
    }

    private static String determineLocalhost() {
        try {
            String hostname = InetAddress.getLocalHost().getHostName();
            jgLog.debug("hostname: " + hostname);
            // trim any extraneous domain info
            String localhost = (hostname.contains(".")) ? hostname.substring(0, hostname.indexOf(".")) : hostname;
            jgLog.debug("localhost: " + localhost);
            return localhost;
        } catch (UnknownHostException e) {
            jgLog.warn(e);
            return "";
        }
    }

    private static String determineSandboxName() {
        if (StringUtil.isEmpty(location)) return "";
        //parse the sandbox dir name from the webapps dir if it exists,
        //if the sandbox does not exist the webapp dir name (eg 'justgive') is return
        // a directory structure that does not contain "/webapps/" may cause this
        // problems loading the correctfile.
        String sandbox = location;
        if (location.indexOf("/webapps/") > 0) {
            sandbox = location.substring(location.indexOf("/webapps/"), location.length());
        }
        sandbox = sandbox.replaceAll("/webapps", "");
        if (sandbox.indexOf("/", 1) > 0) {
            sandbox = sandbox.substring(1, sandbox.indexOf("/", 1));
        }
        jgLog.debug("sandbox: " + sandbox);
        return sandbox;
    }

    private static String composeDefaultHostname(String localhost, String sandbox) {
        try {
            //get the host prefix from hostmapping eg host.just-devapp1 = 'qa'
            String hostPrefix = JustGiveProperties.getString("host." + localhost);
            //get the app prefix from from hostmapping eg app.justgive = 'just'
            String appPrefix = JustGiveProperties.getString("app." + AppProperties.getName());

            jgLog.debug("hostPrefix: " + hostPrefix + " (loaded from host." + localhost + ")");
            jgLog.debug("appPrefix: " + appPrefix + " (loaded from app." + AppProperties.getName());

            String defaultHostname = composeDefaultHostname(hostPrefix, appPrefix, sandbox);
            jgLog.debug("defaultHostname " + defaultHostname);
            return defaultHostname;
        } catch (PropertyException e) {
            jgLog.warn("failed to compose defaultHostname");
            jgLog.warn(e);
            return null;
        }
    }

    private static String composeDefaultHostname(String hostPrefix, String appPrefix, String sandbox) {
        //get the app name from app.properties eg. CURRENT_APP=justgive
        String appName = AppProperties.getName();
        //use these elements to construct to virtual host name, eg: 'just' + 'dev' ( +  '-peter')
        if ("prod".equals(hostPrefix)) {
            if ("justgive".equals(appName)) return "www";
            else return appName;
        } else {
            String defaultHostname = appPrefix + hostPrefix;
            jgLog.debug("composed defaultHostname " + defaultHostname);
            return defaultHostname;
        }
    }

    /**
     * Loads host properties and sets hostname for class
     *
     * @throws PropertyFileNotFoundException if file is not found
     */
    private static boolean loadHostProperties() {
        if (JustGiveProperties.getHostname() == null) return false;
        try {
            JustGiveProperties.loadHostProperties();

            return true;
        } catch (PropertyFileNotFoundException e) {
            jgLog.debug(e.getMessage());
            return false;
        }
    }

    public static Boolean overrideProcessor() {
        if (overrideProcessor != null) return overrideProcessor;
        try {
            overrideProcessor = JustGiveProperties.getBoolean("processor.override");
        } catch (PropertyException e) {
            overrideProcessor = false;
        }
        return overrideProcessor;
    }

    public static Boolean overridePointsProcessor() {
        if (overridePointsProcessor != null) return overridePointsProcessor;
        try {
            overridePointsProcessor = JustGiveProperties.getBoolean("processor.points.override");
        } catch (PropertyException e) {
            overridePointsProcessor = false;
        }
        return overridePointsProcessor;
    }

    public static Boolean overridePointsActivity() {
        if (overridePointsActivity != null) return overridePointsActivity;
        try {
            overridePointsActivity = JustGiveProperties.getBoolean("processor.points.activity.override");
        } catch (PropertyException e) {
            overridePointsActivity = false;
        }
        return overridePointsActivity;
    }

    public static String getHostname() {
        if (hostname == null) hostname = determineLocalhost();
        return hostname;
    }
}
