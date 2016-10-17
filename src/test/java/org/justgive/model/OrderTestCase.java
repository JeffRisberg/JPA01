package org.justgive.model;

import org.junit.BeforeClass;
import org.junit.Test;
import org.justgive.BaseDatabaseTestCase;
import org.justgive.database.DatabaseItemManager;
import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;
import org.justgive.model.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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
    public void basicOrderReportFetch() {
        try {
            EntityManager em = DatabaseItemManager.getInstance().getDatabase().getEntityManager();

            List<Predicate> predList = new ArrayList<Predicate>();

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<OrderInfo> criteria = cb.createQuery(OrderInfo.class);

            Root<Order> order = criteria.from(Order.class);

            Join donor = order.join("donor", JoinType.LEFT);
            Join vendor = order.join("vendor", JoinType.LEFT);
            Join donations = order.join("donations", JoinType.LEFT);

            Subquery giftCertificateSubquery = criteria.subquery(GiftCertificate.class);
            Root giftCertificates = giftCertificateSubquery.from(GiftCertificate.class);
            giftCertificateSubquery.where(cb.equal(giftCertificates.get("order"), order));
            Subquery giftCertificatesAmount = giftCertificateSubquery.select
                    (cb.sum((Expression<BigDecimal>) giftCertificates.get("initialAmount")));

            Subquery gcRedemptionSubquery = criteria.subquery(GiftCertificateRedemption.class);
            Root gcRedemptions = gcRedemptionSubquery.from(GiftCertificateRedemption.class);
            gcRedemptionSubquery.where(cb.equal(gcRedemptions.get("order"), order));
            Subquery gcRedemptionsAmount = gcRedemptionSubquery.select
                    (cb.sum((Expression<BigDecimal>) gcRedemptions.get("amountRedeemed")));

            Subquery feeSubquery = criteria.subquery(Fee.class);
            Root fees = feeSubquery.from(Fee.class);
            feeSubquery.where(cb.equal(fees.get("order"), order));
            Subquery feesAmount = feeSubquery.select
                    (cb.sum((Expression<BigDecimal>) fees.get("amount")));

            predList.add(
                    cb.equal(order.get("orderStatus"), OrderStatus.Completed));

            Predicate[] predArray = new Predicate[predList.size()];
            predList.toArray(predArray);

            criteria.multiselect(order.get("id"),
                    vendor.get("id"), vendor.get("name"),
                    order.get("orderStatus"), order.get("orderSource"),
                    order.get("completedDate"),
                    order.get("referenceCode"), order.get("payPalReferenceCode"),
                    order.get("externalId"),
                    order.get("amountCharged"),
                    cb.sum((Expression<BigDecimal>) donations.get("amount")),
                    cb.sum((Expression<Integer>) donations.get("points")),
                    giftCertificatesAmount.getSelection(),
                    gcRedemptionsAmount.getSelection(),
                    feesAmount.getSelection(),
                    cb.count((Expression<Integer>) donations),
                    donor.get("id"), donor.get("type"), donor.get("emailAddress"),
                    donor.get("firstName"), donor.get("lastName"));

            criteria.groupBy(order.get("id"),
                    vendor.get("id"), vendor.get("name"),
                    order.get("orderStatus"), order.get("orderSource"),
                    order.get("completedDate"),
                    order.get("referenceCode"), order.get("payPalReferenceCode"),
                    order.get("externalId"),
                    order.get("amountCharged"),
                    donor.get("id"), donor.get("type"), donor.get("emailAddress"),
                    donor.get("firstName"), donor.get("lastName"));

            criteria.where(predArray);

            Query query = em.createQuery(criteria);

            query.setFirstResult(0);
            query.setMaxResults(10);

            List<OrderInfo> orderInfos = query.getResultList();

            for (OrderInfo orderInfo : orderInfos) {
                System.out.println(orderInfo.getCompletedDate() + ": id=" + orderInfo.getOrderId());
                System.out.println("  amountCharged " + orderInfo.getAmountCharged());
                System.out.println("  totalDonations " + orderInfo.getTotalDonations());
                System.out.println("  totalPoints " + orderInfo.getTotalPoints());
                System.out.println("  totalRedemptions " + orderInfo.getTotalRedemptions());
                System.out.println("  totalFees " + orderInfo.getTotalFees());
                System.out.println("  numDonations " + orderInfo.getNumDonations());

                assertTrue(orderInfo.getNumDonations() >= 0);

                if (orderInfo.getAmountCharged() != null) {
                    assertTrue(orderInfo.getAmountCharged().doubleValue() >= 0.0);
                }

                if (orderInfo.getTotalDonations() != null) {
                    assertTrue(orderInfo.getTotalDonations().doubleValue() >= 0.0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("basicOrderReportFetch failed");
        }
    }
}

/*

select transaction.id, transaction.order_status, transaction.count, transaction.amount, redemption.amount, fee.amount
  from
    ( select t.id as id, t.order_status as order_status, count(d.id) as count, sum(d.amount) as amount from transactions t left join donations d on d.trans_id = t.id group by t.id ) as transaction
  inner join
    ( select t.id as id, sum(r.amountredeemed) as amount from transactions t left join gc_redemptions r on r.transactionid = t.id group by t.id ) as redemption
  on
    transaction.id = redemption.id
  inner join
    ( select t.id as id, sum(f.amount) as amount from transactions t left join fees f on f.transactionid = t.id group by t.id ) as fee
  on
    transaction.id = fee.id
  where transaction.order_status = 'Completed';

 */