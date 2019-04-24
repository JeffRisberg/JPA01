package com.company;

import com.company.common.FilterDescription;
import com.company.common.FilterOperator;
import com.company.domain.Charity;
import com.company.domain.Donation;
import com.company.domain.Donor;
import com.company.services.CharityService;
import com.company.services.DonationService;
import com.company.services.DonorService;
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

        // Create two charities
        Charity redCross = charityService.create(new Charity("Red Cross", "53-0196605"));
        Charity amCancer = charityService.create(new Charity("American Cancer Society", "13-1788491"));

        // Fetch charities
        List<Charity> charities1 = charityService.getAll(999, 0);
        assertEquals(2, charities1.size());

        // Fetch charity by name
        Charity charity = charityService.getByName("Red Cross");
        assertNotNull(charity);

        // Fetch charities by criteria
        List<FilterDescription> cFilterDescs = new ArrayList<FilterDescription>();
        cFilterDescs.add(new FilterDescription("name", FilterOperator.like, "Red%"));
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

    @Test
    public void testCreateDeleteDonation() {
        DonationService donationService = new DonationService();
        CharityService charityService = new CharityService();
        DonorService donorService = new DonorService();

        // Create a charity
        Charity redCross = charityService.create(new Charity("Red Cross", "53-0196605"));

        // Create a donor
        Donor a = donorService.create(new Donor("Alice", 22));

        // Create a donation
        Donation donation1 = new Donation(45.67);
        donation1.setCharity(redCross);
        donation1.setDonor(a);

        donationService.create(donation1);

        // Fetch donations
        List<Donation> donations1 = donationService.getAll(999, 0);
        assertTrue(donations1.size() == 1);

        // Delete a donation
        donationService.delete(donation1.getId());

        // Fetch donations
        List<Donation> donations2 = donationService.getAll(999, 0);
        assertTrue(donations2.size() == 0);

        donorService.delete(a.getId());
        charityService.delete(redCross.getId());

        donationService.close();
        charityService.close();
        donorService.close();
    }
}
