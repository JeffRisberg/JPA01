package org.justgive.model;

import org.junit.Test;
import org.justgive.BaseDatabaseTestCase;
import org.justgive.database.DatabaseItemManager;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static org.junit.Assert.assertNotNull;
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
                System.out.println(donation);
            }
        } catch (Exception e) {
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

            Query query = em.createQuery(criteria);

            query.setFirstResult(0);
            query.setMaxResults(10);
            List<Donation> donations = query.getResultList();

            assertNotNull(donations);

            for (Donation donation : donations) {
                System.out.println(donation);
            }
        } catch (Exception e) {
            fail("basicFetch failed");
        }
    }
}
