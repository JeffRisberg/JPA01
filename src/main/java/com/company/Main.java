package com.company;

import com.company.domain.Donor;
import com.company.services.DAO.DonorDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;
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

        DonorDAO donorDAO = new DonorDAO();
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        // Create two Donors
        Donor a = donorDAO.create(new Donor("Alice", 22), em); // Alice will get an id 1
        Donor b = donorDAO.create(new Donor("Bob", 20), em); // Bob will get an id 2
        Donor c = donorDAO.create(new Donor("Charlie", 25), em); // Charlie will get an id 3

        // Update the age of Bob using the id
        b.setAge(25);
        donorDAO.update(b, em);

        // Delete Alice from database
        donorDAO.delete(a.getId(), em);

        // Print all the Donors
        List<Donor> donors = donorDAO.listAll(Donor.class, em);
        if (donors != null) {
            for (Donor donor : donors) {
                System.out.println(donor);
            }
        }

        // Delete Bob from the database
        donorDAO.delete(b.getId(), em);

        // Delete Charlie from the database
        donorDAO.delete(c.getId(), em);

        // NEVER FORGET TO CLOSE THE ENTITY_MANAGER_FACTORY
        ENTITY_MANAGER_FACTORY.close();
    }
}