package org.justgive.model;

import org.junit.Test;
import org.justgive.BaseDatabaseTestCase;
import org.justgive.database.DatabaseItemManager;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * @author Jeff Risberg
 * @since 10/3/16
 */
public class OrderTestCase extends BaseDatabaseTestCase {

    @Test
    public void basicFetch() {
        try {
            List<Order> orders = DatabaseItemManager.getInstance().findAll(Order.class, 0, 5);

            assertNotNull(orders);

            for (Order order : orders) {
                Donor donor = order.getDonor();
                //assertNotNull(donor.getId());
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
            CriteriaQuery<Order> criteria = cb.createQuery(Order.class);

            Root<Order> root = criteria.from(Order.class);
            // no joins

            Query query = em.createQuery(criteria);

            query.setFirstResult(0);
            query.setMaxResults(5);

            List<Order> orders = query.getResultList();
            assertNotNull(orders);

            for (Order order : orders) {
                Donor donor = order.getDonor();
                //assertNotNull(donor.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("basicCriteriaFetch failed");
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
            CriteriaQuery<OrderInfo> criteria = cb.createQuery(OrderInfo.class);

            Root<Order> order = criteria.from(Order.class);
            Join donor = order.join("donor", JoinType.LEFT);
            Join vendor = order.join("vendor", JoinType.LEFT);

            predList.add(
                    cb.equal(order.get("orderStatus"), OrderStatus.Completed));

            Predicate[] predArray = new Predicate[predList.size()];
            predList.toArray(predArray);

            criteria.multiselect(order.get("id"),
                    vendor.get("id"), vendor.get("name"),
                    order.get("completedDate"), order.get("externalId"),
                    order.get("amount"), order.get("amountCharged"),
                    donor.get("id"), donor.get("type"), donor.get("emailAddress"),
                    donor.get("firstName"), donor.get("lastName"));

            criteria.where(predArray);

            Query query = em.createQuery(criteria);

            query.setFirstResult(0);
            query.setMaxResults(5);

            List<OrderInfo> orderInfos = query.getResultList();

            for (OrderInfo orderInfo : orderInfos) {
                System.out.println(orderInfo.getCompletedDate() + ": ");
            }

            System.out.println("end basicReportFetch");
        } catch (Exception e) {
            e.printStackTrace();
            fail("basicReportFetch failed");
        }
    }
}
