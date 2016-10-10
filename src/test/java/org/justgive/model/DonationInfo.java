package org.justgive.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jeff
 * @since 10/9/16
 */
public class DonationInfo {
    Integer donationId;
    Integer vendorId;
    String vendorName;
    Integer orderId;
    Date completedDate;
    String orderExternalId;
    Integer donorId;
    String donorEmailAddress;
    String donorFirstName;
    String donorLastName;
    String donorCity;
    String donorState;
    String donorZip;
    Integer charityId;
    String charityName;
    String charityExternalId;
    Float amount;
    String designation;
    Float amountDisbursed;
    BigDecimal processingCharge;
    Integer points;
    Float pointsWeight;
    Boolean shareName;
    Boolean shareEmail;
    Boolean shareAddress;

    public DonationInfo(Integer donationId,
                        Integer vendorId, String vendorName,
                        Integer orderId, Date completedDate, String orderExternalId,
                        Integer donorId, String donorEmailAddress, String donorFirstName, String donorLastName,
                        String donorCity, String donorState, String donorZip,
                        Integer charityId, String charityName, String charityExternalId,
                        Float amount, String designation,
                        Float amountDisbursed, BigDecimal processingCharge,
                        Integer points, Float pointsWeight,
                        Boolean shareName, Boolean shareEmail, Boolean shareAddress) {
        this.donationId = donationId;
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.orderId = orderId;
        this.completedDate = completedDate;
        this.orderExternalId = orderExternalId;
        this.donorId = donorId;
        this.donorEmailAddress = donorEmailAddress;
        this.donorFirstName = donorFirstName;
        this.donorLastName = donorLastName;
        this.donorCity = donorCity;
        this.donorState = donorState;
        this.donorZip = donorZip;
        this.charityId = charityId;
        this.charityName = charityName;
        this.charityExternalId = charityExternalId;
        this.amount = amount;
        this.designation = designation;
        this.amountDisbursed = amountDisbursed;
        this.processingCharge = processingCharge;
        this.points = points;
        this.pointsWeight = pointsWeight;
        this.shareName = shareName;
        this.shareEmail = shareEmail;
        this.shareAddress = shareAddress;
    }

    public Integer getDonationId() {
        return donationId;
    }

    public void setDonationId(Integer donationId) {
        this.donationId = donationId;
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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public String getOrderExternalId() {
        return orderExternalId;
    }

    public void setOrderExternalId(String orderExternalId) {
        this.orderExternalId = orderExternalId;
    }

    public Integer getDonorId() {
        return donorId;
    }

    public void setDonorId(Integer donorId) {
        this.donorId = donorId;
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

    public String getDonorCity() {
        return donorCity;
    }

    public void setDonorCity(String donorCity) {
        this.donorCity = donorCity;
    }

    public String getDonorState() {
        return donorState;
    }

    public void setDonorState(String donorState) {
        this.donorState = donorState;
    }

    public String getDonorZip() {
        return donorZip;
    }

    public void setDonorZip(String donorZip) {
        this.donorZip = donorZip;
    }

    public Integer getCharityId() {
        return charityId;
    }

    public void setCharityId(Integer charityId) {
        this.charityId = charityId;
    }

    public String getCharityName() {
        return charityName;
    }

    public void setCharityName(String charityName) {
        this.charityName = charityName;
    }

    public String getCharityExternalId() {
        return charityExternalId;
    }

    public void setCharityExternalId(String charityExternalId) {
        this.charityExternalId = charityExternalId;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Float getAmountDisbursed() {
        return amountDisbursed;
    }

    public void setAmountDisbursed(Float amountDisbursed) {
        this.amountDisbursed = amountDisbursed;
    }

    public BigDecimal getProcessingCharge() {
        return processingCharge;
    }

    public void setProcessingCharge(BigDecimal processingCharge) {
        this.processingCharge = processingCharge;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Float getPointsWeight() {
        return pointsWeight;
    }

    public void setPointsWeight(Float pointsWeight) {
        this.pointsWeight = pointsWeight;
    }

    public Boolean getShareName() {
        return shareName;
    }

    public void setShareName(Boolean shareName) {
        this.shareName = shareName;
    }

    public Boolean getShareEmail() {
        return shareEmail;
    }

    public void setShareEmail(Boolean shareEmail) {
        this.shareEmail = shareEmail;
    }

    public Boolean getShareAddress() {
        return shareAddress;
    }

    public void setShareAddress(Boolean shareAddress) {
        this.shareAddress = shareAddress;
    }

    public String getPaymentStatus() {
        /*
        if (getOrder() == null || !getOrder().getOrderStatus().equals(OrderStatus.Completed)) {
            return "";
        }

        if (paymentReport != null) {
            return "Sent";
        } else if (disbursementApproval == null || !disbursementApproval.getIsApproved()) {
            return "Pending";
        } else {
            return "Processing";
        }
        */
        return "Processing";
    }
}
