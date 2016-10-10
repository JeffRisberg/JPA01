package org.justgive.services;

import org.junit.BeforeClass;
import org.junit.Test;
import org.justgive.BaseDatabaseTestCase;
import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;
import org.justgive.model.Charity;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * @author Jeffrey Risberg
 * @since 4/23/2015
 */
public class CharityManagerTestCase extends BaseDatabaseTestCase {
    private static Logger jgLog = LoggerFactory.getLogger(CharityManagerTestCase.class);

    static CharityManager charityManager;

    @BeforeClass
    public static void setup() {
        charityManager = CharityManager.getInstance();
    }

    @Test
    public void testFetchCharities() {
        try {
            List<Charity> charities = charityManager.getAllCharities(0, 10);

            assertNotNull(charities);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception fetching charities");
        }
    }
}
