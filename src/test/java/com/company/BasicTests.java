package com.company;

import com.company.common.FilterDesc;
import com.company.common.FilterOperator;
import com.company.domain.Charity;
import com.company.services.CharityService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BasicTests {

    @Test
    public void testCreateDeleteCharity() {
        CharityService charityService = new CharityService();

        assertNotNull(charityService);

        // Create two charities
        Charity redCross = charityService.create(new Charity("Red Cross", "53-0196605"));
        Charity amCancer = charityService.create(new Charity("American Cancer Society", "13-1788491"));

        // Fetch charities
        List<Charity> charities1 = charityService.getAll(999, 0);
        assertEquals(2, charities1.size());

        // Fetch charity by name
        List<Charity> cList = charityService.getByName("Red Cross");
        assertTrue(cList.size() == 1);

        // Fetch charities by criteria
        List<FilterDesc> cFilterDescs = new ArrayList<FilterDesc>();
        cFilterDescs.add(new FilterDesc("name", FilterOperator.like, "Red%"));
        List<Charity> cListCriteria = charityService.getByCriteria(cFilterDescs, 0, 0);
        assertTrue(cListCriteria.size() == 1);

        // Delete two charities
        charityService.delete(redCross.getId());
        charityService.delete(amCancer.getId());

        // Fetch charities
        List<Charity> charities2 = charityService.getAll(999, 0);
        assertTrue(charities2.size() == 0);

        charityService.close();
    }
}
