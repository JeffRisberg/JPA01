package com.company;

import com.company.services.CharityService;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class BasicTests {

    @Test
    public void testCreateDeleteCharity() {
        CharityService charityService = new CharityService();

        assertNotNull(charityService);
    }
}
