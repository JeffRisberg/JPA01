package org.justgive;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.justgive.properties.JustGiveProperties;
import org.justgive.properties.PropertyFileNotFoundException;

/**
 * Base class for loading app properties
 */
public abstract class BaseTestCase {

    @BeforeClass
    public static void oneTimeSetUp() {
        // Do we need the app.properties file? It should be per app - no global file
        JustGiveProperties.addPropertiesFile("config/app");

        // Load optional hostname properties file
        try {
            JustGiveProperties.addPropertiesFile("config/hostname");
            JustGiveProperties.addPropertiesFile(JustGiveProperties.getHostname());
        } catch (PropertyFileNotFoundException e) {
            // Optional property file
        }
    }

    @AfterClass
    public static void oneTimeTearDown() {
        System.out.println("******************* END *****************");
        System.out.println("\n");
    }

    @Before
    public void setUp() {
        //override in implementation
    }

    @After
    public void tearDown() {
        //override in implementation
    }
}