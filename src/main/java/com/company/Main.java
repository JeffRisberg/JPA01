package com.company;

import com.company.common.FilterDesc;
import com.company.common.FilterOperator;
import com.company.domain.Charity;
import com.company.domain.Donor;
import com.company.services.CharityService;
import com.company.services.DonorService;

import java.util.ArrayList;
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
        Donor c = donorService.create(new Donor("Charlie", 45)); // Charlie will get an id 3

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

        // Fetch donor by name
        List<Donor> dList = donorService.getByName("Bob");
        System.out.println(dList.get(0));

        // Fetch donors by criteria
        List<FilterDesc> dFilterDescs = new ArrayList<FilterDesc>();
        dFilterDescs.add(new FilterDesc("age", FilterOperator.gte, 35));
        List<Donor> dListCriteria = donorService.getByCriteria(dFilterDescs, 0, 0);
        System.out.println(dListCriteria.get(0));

        // Delete Bob from the database
        donorService.delete(b.getId());

        // Delete Charlie from the database
        donorService.delete(c.getId());

        // Create two charities
        Charity redCross = charityService.create(new Charity("Red Cross", "53-0196605"));
        Charity amCancer = charityService.create(new Charity("American Cancer Society", "13-1788491"));

        // Fetch charities
        List<Charity> charities = charityService.getAll(999, 0);
        for (Charity charity : charities) {
            System.out.println(charity);
        }

        // Fetch charity by name
        List<Charity> cList = charityService.getByName("Red Cross");
        System.out.println(cList.get(0));

        // Fetch charities by criteria
        List<FilterDesc> cFilterDescs = new ArrayList<FilterDesc>();
        cFilterDescs.add(new FilterDesc("name", FilterOperator.like, "Red%"));
        List<Charity> cListCriteria = charityService.getByCriteria(cFilterDescs, 0, 0);
        System.out.println(cListCriteria.get(0));

        // Delete two charities
        charityService.delete(redCross.getId());
        charityService.delete(amCancer.getId());

        charityService.close();
        donorService.close();
    }
}