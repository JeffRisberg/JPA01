package org.justgive.logger;

import org.justgive.logger.justgive.BufferedLogger;
import org.justgive.logger.justgive.SystemOutLogger;
import org.justgive.logger.wrappers.ClassNameWrapper;
import org.justgive.logger.wrappers.DateTimeWrapper;
import org.justgive.logger.wrappers.LabelWrapper;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 * User: peter c
 * Date: Jan 14, 2008
 * Time: 4:22:13 PM
 */
public class LoggerFactory {
    protected static class ThreadLocalLoggerFactory<T> extends ThreadLocal {
        @Override
        public T initialValue() {
            return (T) new LoggerFactory();
        }

        @Override
        public T get() {
            return (T) super.get();
        }
    }

    protected static ThreadLocalLoggerFactory<LoggerFactory> loggerFactory = new ThreadLocalLoggerFactory<LoggerFactory>();

    protected ResourceBundle loggerProperties;
    protected Boolean loggerDebug = true;
    protected BufferedWriter logBuffer;
    protected HashMap<String, Logger> logs = new HashMap<String, Logger>();
    protected Class defaultType = SystemOutLogger.class;
    protected Class type = defaultType;
    protected String defaultThreshold = "WARN";
    protected String loggerName;

    protected LoggerFactory() {
        initializeProperties();
    }

    public static LoggerFactory getInstance() {
        return loggerFactory.get();
    }

    public static synchronized Logger getLogger(Class loggerClass) {
        return getLogger(loggerClass.getName());
    }

    public static synchronized Logger getLogger(String className) {
        LoggerFactory lf = loggerFactory.get();
        Logger logger;
        if (lf.logs.containsKey(className)) {
            logger = lf.logs.get(className);
        } else {
            logger = loggerFactory.get().newLogger(className);
        }
        return logger;
    }

    protected Logger newLogger(String className) {
        AbstractLogger logger = null;

        try {
            if (this.type.equals(BufferedLogger.class)) {
                if (logBuffer == null) {
                    logBuffer = new BufferedWriter(new OutputStreamWriter(System.out));
                    System.out.println("Created BufferedLogger");
                }
                logger = new BufferedLogger(logBuffer);
            } else {
                logger = (AbstractLogger) this.type.newInstance();
            }
        } catch (Exception e) {
            logger = new SystemOutLogger();
            e.printStackTrace();
        } finally {
            loadThreshold(logger, className);
            logger = wrapLogger(logger, className);
            logs.put(className, logger);
        }
        return logger;
    }

    public void flushLogs() {
        if (logBuffer != null) {
            try {
                logBuffer.flush();
            } catch (IOException e1) {
                if (loggerDebug) System.out.println(e1.getMessage());
            }
        }
    }

    protected void setType(String typeName) {
        try {
            type = Class.forName(typeName);
        } catch (Exception e) {
            type = defaultType;
            System.out.println("using default: " + defaultType);
            e.printStackTrace();
        }
    }

    protected void setThreshold(String threshold) {
        this.defaultThreshold = threshold;
    }

    protected AbstractLogger wrapLogger(AbstractLogger logger, String className) {
        //TODO - define wrappers in properties
        return new DateTimeWrapper(new LabelWrapper(new ClassNameWrapper(logger, className)));
    }

    protected void loadThreshold(AbstractLogger logger, String className) {
        //initialize Logger defaults based on class
        //if no property is found, all exceptions are ignored
        String threshold = null;
        String thresholdName = null;
        if (loggerProperties != null) {
            /*loop through each package up to domain level
                * eg: org.justgive.model.Donor checks
                * org.justgive.model.Donor
                * org.justgive.model
                * org.justgive
                *
                * but not 'org' by itself
                */
            if (loggerDebug) System.out.println("trying to load " + className + ".threshold");
            while (threshold == null && className.lastIndexOf(".") >= 0) {
                thresholdName = className + ".threshold";
                try {
                    threshold = loggerProperties.getString(thresholdName);
                } catch (Exception e) {
                    //System.out.println(className + ".threshold not found");
                }
                //truncate the class name by one section, marked by a "."
                //eg: "org.justgive.model.Donor", becomes "org.justgive.model"
                className = className.substring(0, className.lastIndexOf("."));
            }
        }

        if (threshold == null) {
            if (loggerDebug) System.out.println("using default threshold: " + defaultThreshold);
            logger.setThreshold(Level.getLevel(defaultThreshold));
        } else {
            if (loggerDebug) System.out.println(thresholdName + " loaded: " + threshold);
            logger.setThreshold(Level.getLevel(threshold));
        }
    }

    protected void initializeProperties() {
        //initialize Logger defaults
        //if no properties are found, all exceptions are ignored
        //and hard-coded defaults are used
        loadProperties();

        loadLoggerName();

        loadLoggerType();

        loadLoggerThreshold();
    }

    protected void loadProperties() {
        //loads application Logger properties, if not found,
        //loads default-logger properties as definied in this package
        try {
            loggerProperties = ResourceBundle.getBundle("config/logger");
            loggerDebug = Boolean.valueOf(loggerProperties.getString("logger.debug"));
            if (loggerDebug) System.out.println("logger properties file loaded");
        } catch (Exception e) {
            System.out.println("failed to load logger properties, using default");
            System.out.println(e.getMessage());
            loggerProperties = ResourceBundle.getBundle("config/default-logger");
            if (loggerDebug) System.out.println("default-logger properties file loaded");
        }

        if (loggerDebug) outputLoggerProperties();
    }

    protected void loadLoggerName() {
        try {
            loggerName = loggerProperties.getString("logger.default.name");
            if (loggerDebug) System.out.println("logger.default.name loaded: " + loggerName);
        } catch (MissingResourceException e) {
            System.out.println(e.getMessage());

        }
        if (loggerName == null || loggerName.equals("null")) loggerName = "default";
    }

    protected void loadLoggerType() {
        String type = null;
        try {
            type = loggerProperties.getString("logger." + loggerName + ".type");
            if (loggerDebug) System.out.println("logger." + loggerName + ".type loaded: " + type);
        } catch (MissingResourceException e) {
            System.out.println(e.getMessage());
        }
        if (type == null) {
            try {
                type = loggerProperties.getString("logger." + loggerName + ".type");
                if (loggerDebug) System.out.println("logger.default.type loaded: " + type);
            } catch (MissingResourceException e) {
                System.out.println(e.getMessage());
            }
        }
        if (type != null) {
            this.setType(type);
            if (loggerDebug) System.out.println("set logger Type to: " + type);
        } else {
            if (loggerDebug) System.out.println("LoggerFactory Type not set, using default: " + defaultType);
        }
    }

    protected void loadLoggerThreshold() {
        String threshold = null;
        try {
            threshold = loggerProperties.getString("logger." + loggerName + ".threshold");
            if (loggerDebug) System.out.println("logger." + loggerName + ".threshold loaded:" + threshold);
        } catch (MissingResourceException e) {
            System.out.println(e.getMessage());
        }
        if (threshold == null) {
            try {
                threshold = loggerProperties.getString("logger." + loggerName + ".threshold");
                if (loggerDebug) System.out.println("logger.default.threshold loaded: " + threshold);
            } catch (MissingResourceException e) {
                System.out.println(e.getMessage());
            }
        }
        if (threshold != null) {
            this.setThreshold(threshold);
            if (loggerDebug) System.out.println("Set LoggerFactory Default Threshold to: " + threshold);
        } else {
            if (loggerDebug)
                System.out.println("LoggerFactory Threshold not set, using default: " + defaultThreshold);
        }
    }

    protected void outputLoggerProperties() {
        System.out.println("Contents of logger properties...");
        Enumeration en = loggerProperties.getKeys();
        while (en.hasMoreElements()) {
            String key = (String) en.nextElement();
            System.out.println(key + ": " + loggerProperties.getString(key));
        }
        System.out.println("\nloading logger properties defaults ...");
    }

    @Override
    protected void finalize() throws Throwable {
        if (logBuffer != null) logBuffer.close();
    }
}

