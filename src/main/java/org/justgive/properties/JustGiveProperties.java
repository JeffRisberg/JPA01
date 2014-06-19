package org.justgive.properties;

import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;
import org.justgive.util.StringUtil;

import java.util.*;

/**
 * JustGiveProperties provides a common interface for loading and retrieving values from
 * properties files.  Internally, JustGiveProperties uses ResourceBundle.  Properties files
 * are assumed to be in the classpath as is standard.
 * <p/>
 * User: curtis
 * Date: Aug 14, 2007
 * Time: 2:33:16 PM
 */
public class JustGiveProperties {
    private static Logger jgLog = LoggerFactory.getLogger(JustGiveProperties.class);

    private static Map<String, ResourceBundle> bundles = new HashMap<String, ResourceBundle>();
    private static String hostname = null;

    private JustGiveProperties() {
    }

    /**
     * Loads a properties file with a .properties extension.  The file added must be in the classpath of the
     * application.  This method is called with a String filename argument that can optionally include
     * the .properties file extension.
     * <p/>
     * When loading a properties file name hostname.properties, a check is performed on the
     * property "hostname".  If found, the property's value is used as a property prefix that
     * that is used for local properties that override the regular property's value.
     *
     * @param filename The name of the properties file, either with optional
     *                 .properties filename extension.
     * @throws PropertyFileNotFoundException on a missing properties file
     */
    public static void addPropertiesFile(String filename) throws PropertyFileNotFoundException {
        if (filename == null) {
            throw new IllegalArgumentException("JustGiveProperties.addPropertiesFile(): filename is null");
        }

        filename = trimPropertiesFilename(filename);
        // Add the property file to the Map
        // If added already, return
        if (hasPropertiesFile(filename)) {
            jgLog.info(filename + " has already been added");
            return;
        } else {
            jgLog.trace("attempting to load " + filename + " properties");
        }

        try {
            ResourceBundle bundle = ResourceBundle.getBundle(filename);

            // Check for hostname property
            if (filename.endsWith("hostname")) {
                try {
                    hostname = bundle.getString("hostname");
                    jgLog.info("hostname set to " + hostname);
                } catch (MissingResourceException e) {
                    // Ignore
                }
            } else {
                bundles.put(filename, bundle);
                jgLog.info("properties file " + filename + " loaded");
                System.out.println("properties file " + filename + " loaded");
            }
        } catch (MissingResourceException e) {
            System.out.println("not found properties file " + filename);
            throw new PropertyFileNotFoundException("JustGiveProperties.addPropertiesFile(): File " + filename + " not found");
        }
    }

    /**
     * Returns an enumeration of keys from the named properties file
     *
     * @param filename The name of the properties file, either with optional
     *                 .properties filename extension.
     * @return Enumeration of keys from the named properties file
     * @throws PropertyFileNotFoundException on a missing properties file
     */
    public static Enumeration getResourceBundleKeys(String filename) throws PropertyFileNotFoundException {
        if (filename == null) {
            throw new IllegalArgumentException("JustGiveProperties.getPropertyFileKeys(): filename is null");
        }
        return JustGiveProperties.getResourceBundle(filename).getKeys();
    }

    /**
     * Returns the ResourceBundle from the named properties file
     *
     * @param filename The name of the properties file, either with optional
     *                 .properties filename extension.
     * @return Enumeration of keys from the named properties file
     * @throws PropertyFileNotFoundException on a missing properties file
     */
    public static ResourceBundle getResourceBundle(String filename)
            throws PropertyFileNotFoundException {
        JustGiveProperties.addPropertiesFile(filename);
        ResourceBundle bundle;
        try {
            bundle = JustGiveProperties.getBundle(filename);
        } catch (MissingResourceException e) {
            jgLog.warn(e.getMessage());
            throw new PropertyFileNotFoundException(e);
        }
        return bundle;
    }

    /**
     * returns name of hostname properties file it has been loaded
     *
     * @return name of hostname if set
     */
    public static String getHostname() {
        return hostname;
    }

    /**
     * @param hostname manually sets name of hostname properties file
     */
    public static void setHostname(String hostname) {
        JustGiveProperties.hostname = hostname;
    }

    public static void loadHostProperties(String hostname) {
        // Check filename for "/", remove trailing characters
        JustGiveProperties.hostname = JustGiveProperties.trimPropertiesFilename(hostname);
        jgLog.debug("hostname: " + hostname);

        JustGiveProperties.setHostname(hostname);
        loadHostProperties();
    }

    public static void loadHostProperties() {
        jgLog.debug("loading " + JustGiveProperties.getHostname() + ".properties ");
        JustGiveProperties.addPropertiesFile("config/" + hostname);
        jgLog.debug("config/" + JustGiveProperties.getHostname() + " loaded");
        jgLog.info("hostname set to " + JustGiveProperties.getHostname());
    }

    /**
     * Returns the String value for a property name.
     *
     * @param propertyName The name of the property to find
     * @return String
     * @throws PropertyException When property not found, or when property name argument is empty
     */
    public static String getString(String propertyName) throws PropertyException {
        if (StringUtil.isEmpty(propertyName)) {
            throw new PropertyException("Property name is empty");
        }

        String propertyValue;

        // Check each bundle, return if found
        // Try to find hostname property first
        if (!StringUtil.isEmpty(hostname)) {
            jgLog.trace("looking for host specific property for " + hostname);
            for (String bundleName : bundles.keySet()) {
                ResourceBundle resourceBundle = bundles.get(bundleName);

                try {
                    jgLog.trace("loading " + bundleName + ":property: " + hostname + "." + propertyName);
                    propertyValue = resourceBundle.getString(hostname + "." + propertyName);
                    jgLog.debug(bundleName + ":property: " + hostname + "." + propertyName + " found. value: " + propertyValue);

                    return propertyValue;
                } catch (MissingResourceException e) {
                    jgLog.trace(bundleName + ":property: " + hostname + "." + propertyName + " not found");
                    //Ignore
                }
            }
        }

        for (String bundleName : bundles.keySet()) {
            ResourceBundle resourceBundle = bundles.get(bundleName);

            try {
                propertyValue = resourceBundle.getString(propertyName);
                if (propertyName != null && !propertyName.contains("password")) {
                    jgLog.debug(bundleName + ":property: " + propertyName + " found. value: " + propertyValue);
                } else {
                    jgLog.debug(bundleName + ":property: " + propertyName + " found. value: ********");
                }

                return propertyValue;
            } catch (MissingResourceException e) {
                //Ignore
            }
        }
        jgLog.debug("property: " + propertyName + " not found");
        // Not found, throw exception
        throw new PropertyException("JustGive property not found: " + propertyName);
    }

    private static String getTrimmedString(String propertyName) throws PropertyException {
        String propertyVal = getString(propertyName);
        return (propertyVal == null) ? null : propertyVal.trim();
    }

    /**
     * Returns a String value for a property or null
     * if the property value is empty,
     *
     * @param propertyName The name of the property
     * @return String Property value, or null
     */
    public static String getStringOrNull(String propertyName) {
        String propertyValue;
        try {
            propertyValue = getString(propertyName);
        } catch (PropertyException e) {
            propertyValue = null;
        }
        return propertyValue;
    }

    /**
     * Returns a String value for a property.  If the property value is null,
     * the  propertyName paramter is returned.
     *
     * @param propertyName The name of the property
     * @return String Property value, or property name is value is null
     */
    public static String getStringOrName(String propertyName) {
        String propertyValue;
        try {
            propertyValue = getString(propertyName);
        } catch (PropertyException e) {
            propertyValue = propertyName;
        }
        return propertyValue;
    }

    /**
     * Returns a String value for a property.  If the property value is null,
     * the propertyName paramter is returned. This mimics the behavior of
     * Controller.getProperty(<String>) and should only be used for the
     * initial decoupling phase of refactoring
     *
     * @param propertyName The name of the property
     * @return String Property value, or property name is value is null
     */
    public static String getProperty(String propertyName) {
        String propertyValue;
        try {
            propertyValue = getString(propertyName);
        } catch (PropertyException e) {
            propertyValue = propertyName;
        }
        return propertyValue;
    }

    /**
     * Returns a String value for a property.  If the property value is null,
     * a RequiredPropertyNotFoundException is thrown. This should only be used
     * for the initial decoupling phase of refactoring for properties that must
     * not be null
     *
     * @param propertyName The name of the property
     * @return String Property value
     */
    public static String getRequiredProperty(String propertyName) {
        String propertyValue;
        try {
            propertyValue = getString(propertyName);
        } catch (PropertyException e) {
            throw new RequiredPropertyNotFoundException(e);
        }
        return propertyValue;
    }

    /**
     * Returns an int value for a property.  If the property value is not an integer,
     * a PropertyException is thrown.
     *
     * @param propertyName The name of the property
     * @return int
     * @throws PropertyException on undefined or non-integer property value
     */
    public static int getInt(String propertyName) throws PropertyException {
        String propertyValue = getTrimmedString(propertyName);

        try {
            return Integer.parseInt(propertyValue);
        } catch (NumberFormatException e) {
            jgLog.warn(e.getMessage());
            throw new PropertyException("JustGiveProperties.getInt(): Property " + propertyName + " value not an int");
        }
    }

    /**
     * Returns a long value for a property.  If the property value is not a long, a PropertyException
     * is thrown.
     *
     * @param propertyName The name of the property
     * @return long
     * @throws PropertyException on undefined or non-long property
     */
    public static long getLong(String propertyName) throws PropertyException {
        String propertyValue = getTrimmedString(propertyName);

        try {
            return Long.parseLong(propertyValue);
        } catch (NumberFormatException e) {
            throw new PropertyException("JustGiveProperties.getLong(): Property " + propertyName + " value not a long");
        }
    }

    /**
     * Returns a float value for a property.  If the property value is not a float, a PropertyException
     * is thrown.
     *
     * @param propertyName The name of the property
     * @return float
     * @throws PropertyException on undefined or non-float property
     */
    public static float getFloat(String propertyName) throws PropertyException {
        String propertyValue = getTrimmedString(propertyName);
        try {
            return Float.parseFloat(propertyValue);
        } catch (NumberFormatException e) {
            throw new PropertyException("JustGiveProperties.getLong(): Property " + propertyName + " value not a long");
        }
    }

    /**
     * Returns a boolean value for a property.  If the property value is not a boolean,
     * a PropertyException is thrown.  The value "true" is returned if the property value is equal to "true"
     * (case-insensitive), or equal to "1".
     *
     * @param propertyName The name of the property
     * @return boolean
     * @throws PropertyException on undefined or non-boolean property value
     */
    public static boolean getBoolean(String propertyName) throws PropertyException {
        String propertyValue = getTrimmedString(propertyName);

        return propertyValue.equals("1") || Boolean.parseBoolean(propertyValue);
    }

    /**
     * Returns a list value for a property.  If the property value is not a list, a PropertyException
     * is thrown.
     *
     * @param propertyName The name of the property
     * @return list
     * @throws PropertyException on undefined or non-list property
     */
    public static String[] getStringArray(String propertyName) throws PropertyException {
        String propertyValue = getString(propertyName);
        try {
            return propertyValue.split(",");
        } catch (NumberFormatException e) {
            throw new PropertyException("JustGiveProperties.getLong(): Property " + propertyName + " value not a String[]");
        }
    }


    /**
     * Returns a list value for a property.  If the property value is not a list, a PropertyException
     * is thrown.
     *
     * @param propertyName The name of the property
     * @return list
     * @throws PropertyException on undefined or non-list property
     */
    public static List getList(String propertyName) throws PropertyException {
        String propertyValue = getString(propertyName);
        try {
            return Arrays.asList(propertyValue.split(","));
        } catch (NumberFormatException e) {
            throw new PropertyException("JustGiveProperties.getLong(): Property " + propertyName + " value not a List");
        }
    }

    private static ResourceBundle getBundle(String filename) {
        // Get the property file from the Map
        return ResourceBundle.getBundle(trimPropertiesFilename(filename));
    }

    public static String trimPropertiesFilename(String filename) {
        // Check filename extension, remove trailing ".properties"
        jgLog.trace("attempting to trim '.properties' from " + filename);
        int index = filename.indexOf(".properties");
        if (index != -1) {
            jgLog.trace("'.properties' found");
            filename = filename.substring(0, index);
            jgLog.trace("'.properties' trimmed");
            jgLog.trace(filename);
        }

        // return altered filename
        return filename;
    }

    public static boolean hasPropertiesFile(String filename) {
        if (filename == null) {
            return false;
        } else {
            // trim filename extension
            trimPropertiesFilename(filename);

            // If added already, return true
            return bundles.get(filename) != null;
        }
    }
}
