package com.company;

import com.company.domain.Charity;
import com.company.domain.Donor;
import com.company.services.CharityService;
import com.company.services.DAO.CharityDAO;
import com.company.services.DAO.DonorDAO;
import com.company.services.DonorService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
        Donor a = donorService.create(new Donor(null, "Alice", 22)); // Alice will get an id 1
        Donor b = donorService.create(new Donor(null, "Bob", 20)); // Bob will get an id 2
        Donor c = donorService.create(new Donor(null, "Charlie", 25)); // Charlie will get an id 3

        // Update the age of Bob using the id
        b.setAge(25);
        donorService.update(b);

        // Delete Alice from database
        donorService.delete(a.getId());

        // Print all the Donors
        List<Donor> donors = donorService.getDonors(999, 0);
        for (Donor donor : donors) {
            System.out.println(donor);
        }

        // Delete Bob from the database
        donorService.delete(b.getId());

        // Delete Charlie from the database
        donorService.delete(c.getId());

        // Create a charity
        Charity redCross = new Charity();
        redCross.setName("Red Cross");
        redCross.setEin("57-4444-22343434");

        redCross = charityService.create(redCross);
        System.out.println(redCross.getId());

        // Fetch charities
        List<Charity> charities = charityService.getCharities(999, 0);
        for (Charity charity : charities) {
            System.out.println(charity);
        }

        // Delete a charity
        charityService.delete(redCross.getId());

        //charityService.close();
        //donorService.close();
    }
}