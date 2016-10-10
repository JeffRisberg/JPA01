package org.justgive.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The <i>OrderInfo</i> record holds the fields returned by the reporting query for fetching donation information on
 * the admin action for Orders.
 *
 * @author Jeff Risberg
 * @since 10/9/16
 */
public class OrderInfo {
    Integer orderId;
    Integer vendorId;
    String vendorName;
    OrderStatus orderStatus;
    OrderSource orderSource;
    Date completedDate;
    String referenceCode;
    String batchReferenceCode;
    String merchantReferenceCode;
    String trackingNumber;
    String orderExternalId;
    BigDecimal amount;
    BigDecimal amountCharged;
    Integer donorId;
    Donor.Type donorType;
    String donorEmailAddress;
    String donorFirstName;
    String donorLastName;

    public OrderInfo(Integer orderId,
                     Integer vendorId, String vendorName,
                     OrderStatus orderStatus, OrderSource orderSource,
                     Date completedDate, String orderExternalId,
                     BigDecimal amount, BigDecimal amountCharged,
                     Integer donorId, Donor.Type donorType, String donorEmailAddress,
                     String donorFirstName, String donorLastName) {
        this.orderId = orderId;
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.orderStatus = orderStatus;
        this.orderSource = orderSource;
        this.completedDate = completedDate;
        this.referenceCode = "R--";
        this.batchReferenceCode = "B--";
        this.merchantReferenceCode = "M--";
        this.trackingNumber = "T--";
        this.orderExternalId = orderExternalId;
        this.amount = amount;
        this.amountCharged = amountCharged;
        this.donorId = donorId;
        this.donorType = donorType;
        this.donorEmailAddress = donorEmailAddress;
        this.donorFirstName = donorFirstName;
        this.donorLastName = donorLastName;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OrderSource getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(OrderSource orderSource) {
        this.orderSource = orderSource;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public String getBatchReferenceCode() {
        return batchReferenceCode;
    }

    public void setBatchReferenceCode(String batchReferenceCode) {
        this.batchReferenceCode = batchReferenceCode;
    }

    public String getMerchantReferenceCode() {
        return merchantReferenceCode;
    }

    public void setMerchantReferenceCode(String merchantReferenceCode) {
        this.merchantReferenceCode = merchantReferenceCode;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getOrderExternalId() {
        return orderExternalId;
    }

    public void setOrderExternalId(String orderExternalId) {
        this.orderExternalId = orderExternalId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmountCharged() {
        return amountCharged;
    }

    public void setAmountCharged(BigDecimal amountCharged) {
        this.amountCharged = amountCharged;
    }

    public Integer getDonorId() {
        return donorId;
    }

    public void setDonorId(Integer donorId) {
        this.donorId = donorId;
    }

    public Donor.Type getDonorType() {
        return donorType;
    }

    public void setDonorType(Donor.Type donorType) {
        this.donorType = donorType;
    }

    public String getDonorEmailAddress() {
        return donorEmailAddress;
    }

    public void setDonorEmailAddress(String donorEmailAddress) {
        this.donorEmailAddress = donorEmailAddress;
    }

    public String getDonorFirstName() {
        return donorFirstName;
    }

    public void setDonorFirstName(String donorFirstName) {
        this.donorFirstName = donorFirstName;
    }

    public String getDonorLastName() {
        return donorLastName;
    }

    public void setDonorLastName(String donorLastName) {
        this.donorLastName = donorLastName;
    }
}
