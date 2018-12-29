package com.company;

import com.company.domain.Charity;
import com.company.domain.Donor;
import com.company.services.DAO.CharityDAO;
import com.company.services.DAO.DonorDAO;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class BasicTests {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("JPA01");

    @Test
    public void testCreateDeleteCharity() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        CharityDAO charityDAO = new CharityDAO();

        // Create a charity
        Charity redCross = new Charity();
        redCross.setName("Red Cross");
        redCross.setEin("57-4444-22343434");

        redCross = charityDAO.create(redCross, em);

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
