package org.justgive;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.justgive.config.Environment;
import org.justgive.database.DBSessionFactory;
import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;
import org.justgive.properties.JustGiveProperties;

import java.net.InetAddress;

/**
 * Base class for connecting to the database
 */
public class BaseDatabaseTestCase extends BaseTestCase {
    private static Logger jgLog = LoggerFactory.getLogger(BaseDatabaseTestCase.class);

    @BeforeClass
    public static void oneTimeSetUp() {
        BaseTestCase.oneTimeSetUp();
        String hostName = "unknown";

        try {
            hostName = InetAddress.getLocalHost().getHostName();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("HOSTNAME " + hostName);

        Environment.initialize(hostName);
        JustGiveProperties.addPropertiesFile("config/database");
    }

    @AfterClass
    public static void oneTimeTearDown() {
        try {
            DBSessionFactory.getInstance().commitTransaction();
        } catch (Exception e) {
            jgLog.error("ERROR COMMITTING DB TRANSACTION: " + e.getMessage());
        }

        try {
            DBSessionFactory.getInstance().closeTransaction();
        } catch (Exception e) {
            jgLog.error("ERROR CLOSING DB TRANSACTION: " + e.getMessage());
        }
    }
}
