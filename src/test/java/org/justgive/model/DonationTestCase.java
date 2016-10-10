package org.justgive.model;

import org.junit.Test;
import org.justgive.BaseDatabaseTestCase;
import org.justgive.database.DatabaseItemManager;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Jeff Risberg
 * @since 10/3/16
 */
public class DonationTestCase extends BaseDatabaseTestCase {

    @Test
    public void basicFetch() {
        try {
            List<Donation> donations = DatabaseItemManager.getInstance().findAll(Donation.class, 0, 5);

            assertNotNull(donations);

            for (Donation donation : donations) {
                System.out.println(donation + " " + donation.getCharity());
                assertTrue(donation.getAmount() >= 0.0);

                Charity charity = donation.getCharity();
                assertNotNull(charity.getName());

                Order order = donation.getOrder();
                assertNotNull(order.getId());

                Donor donor = order.getDonor();
                assertNotNull(donor.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("basicFetch failed");
        }
    }

    @Test
    public void basicCriteriaFetch() {
        try {
            // Set up the criteria
            EntityManager em = DatabaseItemManager.getInstance().getDatabase().getEntityManager();

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Donation> criteria = cb.createQuery(Donation.class);

            Root<Donation> root = criteria.from(Donation.class);
            // no joins

            Query query = em.createQuery(criteria);

            query.setFirstResult(0);
            query.setMaxResults(5);

            List<Donation> donations = query.getResultList();
            assertNotNull(donations);

            for (Donation donation : donations) {
                System.out.println(donation + " " + donation.getCharity());
                assertTrue(donation.getAmount() >= 0.0);

                Charity charity = donation.getCharity();
                assertNotNull(charity.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("basicCriteriaFetch failed");
        }
    }

    @Test
    public void basicCriteriaWithJoinFetch() {
        try {
            // Set up the criteria
            EntityManager em = DatabaseItemManager.getInstance().getDatabase().getEntityManager();

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Donation> criteria = cb.createQuery(Donation.class);

            Root<Donation> root = criteria.from(Donation.class);
            root.fetch("charity", JoinType.INNER);

            Query query = em.createQuery(criteria);

            query.setFirstResult(0);
            query.setMaxResults(5);

            List<Donation> donations = query.getResultList();
            assertNotNull(donations);

            for (Donation donation : donations) {
                System.out.println(donation + " " + donation.getCharity());
                assertTrue(donation.getAmount() >= 0.0);

                Charity charity = donation.getCharity();
                assertNotNull(charity.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("basicCriteriawithJoinFetch failed");
        }
    }

    //@Test
    public void basicReportFetch() {
        try {
            // Set up the criteria
            EntityManager em = DatabaseItemManager.getInstance().getDatabase().getEntityManager();

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Donation> criteria = cb.createQuery(Donation.class);

            Root<Donation> root = criteria.from(Donation.class);
            root.fetch("charity", JoinType.INNER);

            Query query = em.createQuery(criteria);

        } catch (Exception e) {
            e.printStackTrace();
            fail("basicCriteriawithJoinFetch failed");
        }
    }
}
