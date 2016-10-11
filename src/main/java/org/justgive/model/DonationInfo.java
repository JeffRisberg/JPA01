package org.justgive.model;

import org.justgive.model.OrderSource;
import org.justgive.model.OrderStatus;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The <i>DonationInfo</i> record holds the fields returned by the reporting query for fetching donation
 * information on the admin action for Donations.
 *
 * @author Jeff Risberg
 * @since 10/9/16
 */
public class DonationInfo {
    private Integer donationId;
    private Integer vendorId;
    private String vendorName;
    private Integer orderId;
    private OrderStatus orderStatus;
    private OrderSource orderSource;
    private Date orderCompletedDate;
    private String orderReferenceCode;
    private String orderMerchantReferenceCode;
    private String orderExternalId;
    private Integer donorId;
    private Donor.Type donorType;
    private String donorEmailAddress;
    private String donorFirstName;
    private String donorLastName;
    private String donorCity;
    private String donorState;
    private String donorZip;
    private Integer charityId;
    private String charityName;
    private String charityExternalId;
    private Float amount;
    private String designation;
    private String recipientName;
    private String memorialName;
    private Float amountDisbursed;
    private BigDecimal processingCharge;
    private Integer points;
    private Float pointsWeight;
    private Boolean shareName;
    private Boolean shareEmail;
    private Boolean shareAddress;
    private String certificateID;
    private Boolean disbursementApproved;
    private Integer paymentReportId;
    private String invoiceNumber;
    private String checkNumber;

    public DonationInfo() { // required for this to be a bean
    }

    public DonationInfo(Integer donationId,
                        Integer vendorId, String vendorName,
                        Integer orderId, OrderStatus orderStatus, OrderSource orderSource,
                        Date orderCompletedDate, String orderExternalId,
                        Integer donorId, Donor.Type donorType, String donorEmailAddress, String donorFirstName, String donorLastName,
                        String donorCity, String donorState, String donorZip,
                        Integer charityId, String charityName, String charityExternalId,
                        Float amount, String designation,
                        String recipientName, String memorialName,
                        Float amountDisbursed, BigDecimal processingCharge,
                        Integer points, Float pointsWeight,
                        Boolean shareName, Boolean shareEmail, Boolean shareAddress,
                        String certificateID, Boolean disbursementApproved,
                        Integer paymentReportId, String invoiceNumber, String checkNumber) {
        this.donationId = donationId;
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderSource = orderSource;
        this.orderCompletedDate = orderCompletedDate;
        this.orderReferenceCode = "---";
        this.orderMerchantReferenceCode = "---";
        this.orderExternalId = orderExternalId;
        this.donorId = donorId;
        this.donorType = donorType;
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
        this.recipientName = recipientName;
        this.memorialName = memorialName;
        this.amountDisbursed = amountDisbursed;
        this.processingCharge = processingCharge;
        this.points = points;
        this.pointsWeight = pointsWeight;
        this.shareName = shareName;
        this.shareEmail = shareEmail;
        this.shareAddress = shareAddress;
        this.certificateID = certificateID;
        this.disbursementApproved = disbursementApproved;
        this.paymentReportId = paymentReportId;
        this.invoiceNumber = invoiceNumber;
        this.checkNumber = checkNumber;
    }

    public Integer getDonationId() {
        return donationId;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public OrderSource getOrderSource() {
        return orderSource;
    }

    public Date getOrderCompletedDate() {
        return orderCompletedDate;
    }

    public String getOrderReferenceCode() {
        return orderReferenceCode;
    }

    public String getOrderMerchantReferenceCode() {
        return orderMerchantReferenceCode;
    }

    public String getOrderExternalId() {
        return orderExternalId;
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

    public String getDonorCity() {
        return donorCity;
    }

    public String getDonorState() {
        return donorState;
    }

    public String getDonorZip() {
        return donorZip;
    }

    public Integer getCharityId() {
        return charityId;
    }

    public String getCharityName() {
        return charityName;
    }

    public String getCharityExternalId() {
        return charityExternalId;
    }

    public Float getAmount() {
        return amount;
    }

    public String getDesignation() {
        return designation;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getMemorialName() {
        return memorialName;
    }

    public Float getAmountDisbursed() {
        return amountDisbursed;
    }

    public BigDecimal getProcessingCharge() {
        return processingCharge;
    }

    public Integer getPoints() {
        return points;
    }

    public Float getPointsWeight() {
        return pointsWeight;
    }

    public Boolean getShareName() {
        return shareName;
    }

    public Boolean getShareEmail() {
        return shareEmail;
    }

    public Boolean getShareAddress() {
        return shareAddress;
    }

    public String getCertificateID() {
        return certificateID;
    }

    public Integer getPaymentReportId() {
        return paymentReportId;
    }

    public Boolean getDisbursementApproved() {
        return disbursementApproved;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public String getPaymentStatus() {
        if (orderStatus == null || !orderStatus.equals(OrderStatus.Completed)) {
            return "";
        }

        if (paymentReportId != null) {
            return "Sent";
        } else if (disbursementApproved == null || !disbursementApproved) {
            return "Pending";
        } else {
            return "Processing";
        }
    }
}
