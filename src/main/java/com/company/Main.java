package com.company;

import com.company.domain.Charity;
import com.company.domain.Donor;
import com.company.services.DAO.CharityDAO;
import com.company.services.DAO.DonorDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * @author Jeff Risberg
 * @since 11/3/17
 */
public class Main {
    // Create an EntityManagerFactory when you start the application.
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("JPA01");

    public static void main(String[] args) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        DonorDAO donorDAO = new DonorDAO();
        CharityDAO charityDAO = new CharityDAO();

        // Create two Donors
        Donor a = donorDAO.create(new Donor(null, "Alice", 22), em); // Alice will get an id 1
        Donor b = donorDAO.create(new Donor(null, "Bob", 20), em); // Bob will get an id 2
        Donor c = donorDAO.create(new Donor(null, "Charlie", 25), em); // Charlie will get an id 3

        // Update the age of Bob using the id
        b.setAge(25);
        donorDAO.update(b, em);

        // Delete Alice from database
        donorDAO.delete(a.getId(), em);

        // Print all the Donors
        List<Donor> donors = donorDAO.listAll(Donor.class, em);
        for (Donor donor : donors) {
            System.out.println(donor);
        }

        // Delete Bob from the database
        donorDAO.delete(b.getId(), em);

        // Delete Charlie from the database
        donorDAO.delete(c.getId(), em);

        // Create a charity
        Charity redCross = new Charity();
        redCross.setName("Red Cross");
        redCross.setEin("57-4444-22343434");

        redCross = charityDAO.create(redCross, em);
        System.out.println(redCross.getId());

        // Fetch charities
        List<Charity> charities = charityDAO.listAll(Charity.class, em);
        for (Charity charity : charities) {
            System.out.println(charity);
        }

        // Delete a charity
        charityDAO.delete(redCross.getId(), em);

        // NEVER FORGET TO CLOSE THE ENTITY_MANAGER_FACTORY
        ENTITY_MANAGER_FACTORY.close();
    }
}