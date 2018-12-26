package com.company;

import com.company.domain.Donor;

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

        // Create two Donors
        create("Alice", 22); // Alice will get an id 1
        create("Bob", 20); // Bob will get an id 2
        create("Charlie", 25); // Charlie will get an id 3

        // Update the age of Bob using the id
        update("Bob", 25);

        // Delete the Alice from database
        delete(1);

        // Print all the Donors
        List<Donor> donors = readAll();
        if (donors != null) {
            for (Donor donor : donors) {
                System.out.println(donor);
            }
        }

        // NEVER FORGET TO CLOSE THE ENTITY_MANAGER_FACTORY
        ENTITY_MANAGER_FACTORY.close();
    }

    /**
     * Create a new Donor.
     *
     * @param name
     * @param age
     */
    public static Donor create(String name, int age) {
        // Create an EntityManager
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Create a new Donor object
            Donor donor = new Donor();
            donor.setCreatedAt(new Date());
            donor.setName(name);

            // Save the donor object
            manager.persist(donor);

            // Commit the transaction
            transaction.commit();
        } catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            ex.printStackTrace();
        } finally {
            // Close the EntityManager
            manager.close();
        }
    }

    /**
     * Read all the Donors.
     *
     * @return a List of Donors
     */
    public static List<Donor> readAll() {

        List<Donor> donors = null;

        // Create an EntityManager
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Get a List of Donors
            donors = manager.createQuery("SELECT s FROM Donor s", Donor.class).getResultList();

            // Commit the transaction
            transaction.commit();
        } catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            ex.printStackTrace();
        } finally {
            // Close the EntityManager
            manager.close();
        }
        return donors;
    }

    /**
     * Update the existing Donor.
     *
     * @param id
     * @param name
     * @param age
     */
    public static void update(int id, String name, int age) {
        // Create an EntityManager
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Get the Donor object
            Donor donor = manager.find(Donor.class, id);

            // Change the values
            donor.setName(name);

            // Update the donor
            manager.persist(donor);

            // Commit the transaction
            transaction.commit();
        } catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            ex.printStackTrace();
        } finally {
            // Close the EntityManager
            manager.close();
        }
    }

    /**
     * Delete the existing Donor.
     *
     * @param id
     */
    public static void delete(int id) {
        // Create an EntityManager
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Get the Donor object
            Donor donor = manager.find(Donor.class, id);

            // Delete the donor
            manager.remove(donor);

            // Commit the transaction
            transaction.commit();
        } catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            ex.printStackTrace();
        } finally {
            // Close the EntityManager
            manager.close();
        }
    }
}