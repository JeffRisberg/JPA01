package org.justgive.config;

import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;
import org.justgive.properties.JustGiveProperties;
import org.justgive.properties.PropertyException;
import org.justgive.properties.PropertyFileNotFoundException;
import org.justgive.properties.RequiredPropertyNotFoundException;
import org.justgive.util.StringUtil;

public class AppProperties {
    private static Logger jgLog = LoggerFactory.getLogger(AppProperties.class);

    private static String name = "";
    private static Boolean encryptSensitiveData = false;
    private static String supportEmail = "";
    private static Boolean hasPoints = false;
    private static Boolean hasVendors = false;
    private static String partnershipRequestEmail = "";
    private static String uploadDir = "";
    private static Boolean storeSessionCarts = false;
    private static String processorName;

    private static Boolean initialized = false;

    private AppProperties() {
    }

    public static void initialize() {
        if (!initialized) {
            synchronized (AppProperties.class) {
                if (!initialized) {
                    boolean isWebservice = isWebservice();
                    try {
                        name = JustGiveProperties.getString("app.name");
                        encryptSensitiveData = JustGiveProperties.getBoolean("app.encrypt.donor.data");

                        if (!isWebserviceAdmin() && !isReports()) {
                            if (!isWebservice) {
                                supportEmail = JustGiveProperties.getString("app.support.email");
                                hasPoints = JustGiveProperties.getBoolean("app.has.points");
                                hasVendors = JustGiveProperties.getBoolean("app.has.vendors");
                                partnershipRequestEmail = JustGiveProperties.getString("app.partnership.request.email");
                                uploadDir = JustGiveProperties.getString("app.upload.dir");
                                storeSessionCarts = Boolean.valueOf(
                                        JustGiveProperties.getStringOrNull("app.store.session.carts"));
                            }
                            String procAccount = JustGiveProperties.getProperty("processor.account");
                            processorName = JustGiveProperties.getProperty("processor." + procAccount + ".name");
                            loadPaymentProcessorProperties();
                            loadGeocoderProperties();
                        }
                    } catch (PropertyException e) {
                        jgLog.error(e);
                        throw new RequiredPropertyNotFoundException(e.getMessage());
                    }

                    initialized = true;
                }
            }
        }
    }

    private static boolean isWebservice() {
        boolean isWebservice = false;
        try {
            // Load application properties file
            try {
                JustGiveProperties.addPropertiesFile("config/app");
            } catch (PropertyFileNotFoundException e) {
                jgLog.debug(e.getMessage());
                JustGiveProperties.addPropertiesFile("app");
                isWebservice = true;
            }
            try {
                JustGiveProperties.addPropertiesFile("config/buildVersion");
            } catch (PropertyFileNotFoundException e) {
                jgLog.debug(e.getMessage());
            }
        } catch (PropertyFileNotFoundException e) {
            jgLog.debug(e.getMessage());
            // Load web service properties file
            JustGiveProperties.addPropertiesFile("service");
            isWebservice = true;
        }
        return isWebservice;
    }

    private static boolean isWebserviceAdmin() {
        return !StringUtil.isEmpty(name) && name.equals("webserviceadmin");
    }

    private static boolean isReports() {
        return !StringUtil.isEmpty(name) && name.endsWith("-reports");
    }

    private static void loadPaymentProcessorProperties() {// Load optional payment-processor properties file
        try {
            jgLog.debug("loading payment-processor properties");
            JustGiveProperties.addPropertiesFile("config/payment-processor");
        } catch (PropertyFileNotFoundException e) {
            // Optional property file
            jgLog.debug(e.getMessage());
        }
    }

    private static void loadGeocoderProperties() {// Load optional geocoder properties file
        try {
            jgLog.debug("loading geocoder properties");
            JustGiveProperties.addPropertiesFile("config/geocoder.properties");
        } catch (PropertyFileNotFoundException e) {
            // Optional property file
            jgLog.debug(e.getMessage());
        }
    }

    public static String getName() {
        initialize();
        return name;
    }

    public static Boolean encryptSensitiveData() {
        initialize();
        return encryptSensitiveData;
    }

    @Deprecated //see PortalAttributes.supportEmail
    public static String getSupportEmail() {
        initialize();
        return supportEmail;
    }

    public static Boolean hasPoints() {
        initialize();
        return hasPoints;
    }

    public static Boolean hasVendors() {
        initialize();
        return hasVendors;
    }

    public static String getPartnershipRequestEmail() {
        initialize();
        return partnershipRequestEmail;
    }

    public static String getUploadDir() {
        initialize();
        return uploadDir;
    }

    public static Boolean getStoreSessionCarts() {
        initialize();
        return storeSessionCarts;
    }

    public static String getProcessorName() {
        initialize();
        return processorName;
    }

    public static boolean isPayPalProcessor() {
        boolean paypal = getProcessorName().equals("paypal");
        jgLog.debug("isPaypal? " + paypal);
        return paypal;
    }
}
