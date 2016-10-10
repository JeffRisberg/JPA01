package org.justgive.model;

import org.junit.Test;
import org.justgive.BaseDatabaseTestCase;
import org.justgive.database.DatabaseItemManager;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Jeff Risberg
 * @since 10/3/16
 */
public class DonationTestCase extends BaseDatabaseTestCase {

    //@Test
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

    //@Test
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

    //@Test
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

                Order order = donation.getOrder();
                assertNotNull(order.getId());

                Vendor vendor = order.getVendor();
                assertNotNull(vendor.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("basicCriteriawithJoinFetch failed");
        }
    }

    @Test
    public void basicReportFetch() {
        try {
            System.out.println("begin basicReportFetch");

            // Set up the criteria
            EntityManager em = DatabaseItemManager.getInstance().getDatabase().getEntityManager();

            List<Predicate> predList = new ArrayList<Predicate>();

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<DonationInfo> criteria = cb.createQuery(DonationInfo.class);

            Root<Donation> donation = criteria.from(Donation.class);
            Join order = donation.join("order", JoinType.INNER);
            Join donor = order.join("donor", JoinType.LEFT);
            Join vendor = order.join("vendor", JoinType.LEFT);
            Join charity = donation.join("charity", JoinType.INNER);

            predList.add(
                    cb.equal(order.get("orderStatus"), OrderStatus.Completed));

            Predicate[] predArray = new Predicate[predList.size()];
            predList.toArray(predArray);

            criteria.multiselect(donation.get("id"),
                    vendor.get("id"), vendor.get("name"),
                    order.get("id"), order.get("completedDate"), order.get("externalId"),
                    donor.get("id"), donor.get("type"), donor.get("emailAddress"),
                    donor.get("firstName"), donor.get("lastName"),
                    donor.get("city"), donor.get("state"), donor.get("zip"),
                    charity.get("id"), charity.get("name"), charity.get("externalId"),
                    donation.get("amount"), donation.get("designation"),
                    donation.get("amountDisbursed"), donation.get("processingCharge"),
                    donation.get("points"), donation.get("pointsWeight"),
                    donation.get("shareName"), donation.get("shareEmail"), donation.get("shareAddress"));

            criteria.where(predArray);

            Query query = em.createQuery(criteria);

            query.setFirstResult(0);
            query.setMaxResults(5);

            List<DonationInfo> donationInfos = query.getResultList();

            for (DonationInfo donationInfo : donationInfos) {
                System.out.println(donationInfo.getCompletedDate() + ": " +
                        donationInfo.getDonorFirstName() + " " + donationInfo.getDonorLastName() + " " +
                        donationInfo.getCharityName() + " " + donationInfo.getAmount());
                assertTrue(donationInfo.getAmount() >= 0.0);
            }

            System.out.println("end basicReportFetch");
        } catch (Exception e) {
            e.printStackTrace();
            fail("basicReportFetch failed");
        }
    }
}
