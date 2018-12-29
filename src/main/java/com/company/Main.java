package com.company;

import com.company.domain.Charity;
import com.company.domain.Donor;
import com.company.services.CharityService;
import com.company.services.DonorService;

import java.util.List;

/**
 * @author Jeff Risberg
 * @since 11/3/17
 */
public class Main {
    public static void main(String[] args) {
        DonorService donorService = new DonorService();
        CharityService charityService = new CharityService();

        // Create two Donors
        Donor a = donorService.create(new Donor("Alice", 22)); // Alice will get an id 1
        Donor b = donorService.create(new Donor("Bob", 20)); // Bob will get an id 2
        Donor c = donorService.create(new Donor("Charlie", 25)); // Charlie will get an id 3

        // Update the age of Bob using the id
        b.setAge(25);
        donorService.update(b);

        // Delete Alice from database
        donorService.delete(a.getId());

        // Print all the Donors
        List<Donor> donors = donorService.getAll(999, 0);
        for (Donor donor : donors) {
            System.out.println(donor);
        }

        // Delete Bob from the database
        donorService.delete(b.getId());

        // Delete Charlie from the database
        donorService.delete(c.getId());

        // Create two charities
        Charity redCross = charityService.create(new Charity("Red Cross", "54-3254367"));
        Charity amCancer = charityService.create(new Charity("American Cancer Soc", "22-8435678"));

        // Fetch charities
        List<Charity> charities = charityService.getAll(999, 0);
        for (Charity charity : charities) {
            System.out.println(charity);
        }

        // Delete two charities
        charityService.delete(redCross.getId());
        charityService.delete(amCancer.getId());

        charityService.close();
        donorService.close();
    }
}