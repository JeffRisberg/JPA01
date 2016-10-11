package org.justgive.model;

import org.justgive.model.Donor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The <i>OrderInfo</i> record holds the fields returned by the reporting query for fetching order information on
 * the admin action for Orders.
 *
 * @author Jeff Risberg
 * @since 10/9/16
 */
public class OrderInfo {
    private Integer orderId;
    private Integer vendorId;
    private String vendorName;
    private OrderStatus orderStatus;
    private OrderSource orderSource;
    private Date completedDate;
    private String referenceCode;
    private String payPalReferenceCode;
    private String claimCode;
    private String orderExternalId;
    private BigDecimal amountCharged = new BigDecimal(0.0);
    private Double totalDonations = new Double(0.0);
    private Long totalPoints = 0L;
    private BigDecimal totalGiftCertProducts = new BigDecimal(0.0);
    private BigDecimal totalRedemptions = new BigDecimal(0.0);
    private BigDecimal totalFees = new BigDecimal(0.0);
    private Long numDonations = 0L;
    private Integer donorId;
    private Donor.Type donorType;
    private String donorEmailAddress;
    private String donorFirstName;
    private String donorLastName;

    public OrderInfo() { // required for this to be a bean
    }

    public OrderInfo(Integer orderId,
                     Integer vendorId, String vendorName,
                     OrderStatus orderStatus, OrderSource orderSource,
                     Date completedDate, String referenceCode, String payPalReferenceCode, String orderExternalId,
                     BigDecimal amountCharged, Double totalDonations, Long totalPoints,
                     BigDecimal totalGiftCertProducts, BigDecimal totalRedemptions, BigDecimal totalFees,
                     Long numDonations,
                     Integer donorId, Donor.Type donorType, String donorEmailAddress,
                     String donorFirstName, String donorLastName) {
        this.orderId = orderId;
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.orderStatus = orderStatus;
        this.orderSource = orderSource;
        this.completedDate = completedDate;
        this.referenceCode = referenceCode;
        this.payPalReferenceCode = payPalReferenceCode;
        this.claimCode = "C--";
        this.orderExternalId = orderExternalId;
        this.amountCharged = amountCharged;
        this.totalDonations = totalDonations;
        this.totalPoints = totalPoints;
        this.totalGiftCertProducts = totalGiftCertProducts;
        this.totalRedemptions = totalRedemptions;
        this.totalFees = totalFees;
        this.numDonations = numDonations;
        this.donorId = donorId;
        this.donorType = donorType;
        this.donorEmailAddress = donorEmailAddress;
        this.donorFirstName = donorFirstName;
        this.donorLastName = donorLastName;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public OrderSource getOrderSource() {
        return orderSource;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public String getPayPalReferenceCode() {
        return payPalReferenceCode;
    }

    public String getClaimCode() {
        return claimCode;
    }

    public String getOrderExternalId() {
        return orderExternalId;
    }

    public BigDecimal getAmountCharged() {
        return amountCharged;
    }

    public Double getTotalDonations() {
        return totalDonations;
    }

    public Long getTotalPoints() {
        return totalPoints;
    }

    public BigDecimal getTotalGiftCertProducts() {
        return totalGiftCertProducts;
    }

    public BigDecimal getTotalRedemptions() {
        return totalRedemptions;
    }

    public BigDecimal getTotalFees() {
        return totalFees;
    }

    public Long getNumDonations() {
        return numDonations;
    }

    public Integer getDonorId() {
        return donorId;
    }

    public Donor.Type getDonorType() {
        return donorType;
    }

    public String getDonorEmailAddress() {
        return donorEmailAddress;
    }

    public String getDonorFirstName() {
        return donorFirstName;
    }

    public String getDonorLastName() {
        return donorLastName;
    }
}
