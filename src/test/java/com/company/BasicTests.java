package com.company;

import com.company.domain.Charity;
import com.company.services.CharityService;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BasicTests {

    @Test
    public void testCreateDeleteCharity() {
        CharityService charityService = new CharityService();

        assertNotNull(charityService);

        // Create two charities
        Charity redCross = charityService.create(new Charity("Red Cross", "54-3254367"));
        Charity amCancer = charityService.create(new Charity("American Cancer Soc", "22-8435678"));

        // Fetch charities
        List<Charity> charities1 = charityService.getAll(999, 0);
        assertTrue(charities1.size() == 2);

        // Delete two charities
        charityService.delete(redCross.getId());
        charityService.delete(amCancer.getId());

        // Fetch charities
        List<Charity> charities2 = charityService.getAll(999, 0);
        assertTrue(charities2.size() == 0);

        charityService.close();
    }
}
