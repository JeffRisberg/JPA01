package org.justgive.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.justgive.database.DatedDatabaseItem;
import org.justgive.exceptions.JustGiveException;
import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;
import org.justgive.util.MoneyMath;
import org.justgive.util.StringUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

//import org.jails.validation.constraint.ISOCountryCode;
//import org.jails.validation.constraint.ISOStateCode;
//import org.jails.validation.constraint.NotNullDepends;
//import org.jails.validation.constraint.RequiredChecks;

/*
@NotNullDepends.List({
        @NotNullDepends(field = "recipientEmail",
                dependsField = "format", dependsValue = GiftCertificate.ELECTRONIC),
        @NotNullDepends(field = "recipientAddressLine1",
                dependsField = "format", dependsValue = GiftCertificate.PHYSICAL),
        @NotNullDepends(field = "recipientCity",
                dependsField = "format", dependsValue = GiftCertificate.PHYSICAL),
        @NotNullDepends(field = "recipientState",
                dependsField = "format", dependsValue = GiftCertificate.PHYSICAL),
        @NotNullDepends(field = "recipientZip",
                dependsField = "format", dependsValue = GiftCertificate.PHYSICAL),
        @NotNullDepends(field = "recipientCountry",
                dependsField = "format", dependsValue = GiftCertificate.PHYSICAL)
})
*/
@Entity
@Table(name = "giftcertificates")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "giftcertificates")
public class GiftCertificate extends DatedDatabaseItem {
    private static Logger jgLog = LoggerFactory.getLogger(GiftCertificate.class);

    private static DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT);

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "trans_id")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "orders")
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchaserid")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "donors")
    private Donor purchaser;

    @Size(max = 255)
    @Column(name = "purchaser_email")
    private String purchaserEmail;

    @Size(max = 50)
    @Column(name = "purchaser_name")
    private String purchaserName;

    @NotNull(/*groups = RequiredChecks.class*/)
    @Column(name = "recipientid")
    private Integer recipientId;

    @Column(name = "claimCode")
    private String claimCode;

    //@Column(name = "trackingnumber")
    //todo - remove?
    @Transient
    private String trackingNumber;  //JustGive assigned tracking number

    @NotNull(/*groups = RequiredChecks.class*/)
    private BigDecimal initialAmount;

    @NotNull(/*groups = RequiredChecks.class*/)
    private BigDecimal amountBalance;

    @Column(name = "dateactivated")
    private Date dateActivated;

    @Column(name = "canceldate")
    private Date cancelDate;

    @Column(name = "datetosend")
    private Date dateToSend;

    @Column
    private Boolean isActive = false;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Size(max = 255)
    private String message;

    @Column(name = "recipient_name")
    @Size(max = 50)
    private String recipientName;
    @Size(max = 255)
    private String recipientEmail;
    @Size(max = 100)
    private String recipientAddress1;
    @Size(max = 100)
    private String recipientAddress2;
    @Size(max = 50)
    private String recipientCity;
    @Size(max = 25)
    //@ISOStateCode
    private String recipientState;
    @Size(max = 15)
    private String recipientZip;
    @Size(max = 100)
    //@ISOCountryCode
    private String recipientCountry;

    // The amount weight at time of purchase
    // Varies by vendor
    @Column(name = "amount_weight")
    private Float amountWeight;

    /**
     * Default Constructor
     */
    public GiftCertificate() {
    }

    public GiftCertificate(BigDecimal amount, Long count) {
        this.amountBalance = amount == null ? MoneyMath.ZERO : amount;
        setId(count == null ? 0 : count.intValue());
    }

    public void setOrder(Order newValue) {
        this.order = newValue;
    }

    public Order getOrder() {
        return order;
    }

    public void setPurchaser(Donor newValue) {
        this.purchaser = newValue;
    }

    public Donor getPurchaser() {
        return this.purchaser;
    }

    public String getPurchaserEmail() {
        return purchaserEmail;
    }

    public void setPurchaserEmail(String purchaserEmail) {
        this.purchaserEmail = purchaserEmail;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public void setRecipientId(Integer newValue) {
        this.recipientId = newValue;
    }

    public Integer getRecipientId() {
        return this.recipientId;
    }

    /**
     * @see this.getClaimCode
     * @deprecated
     */
    @SuppressWarnings({"JavaDoc"})
    public String getPurchaseNumber() {
        return getClaimCode();
    }

    public void setClaimCode(String claimCode) {
        this.claimCode = claimCode;
    }

    public String getClaimCode() {
        return claimCode;
    }

    public void setTrackingNumber(String newValue) {
        this.trackingNumber = newValue;
    }

    public String getTrackingNumber() {
        return this.trackingNumber;
    }

    public void setInitialAmount(BigDecimal newValue) {
        this.initialAmount = newValue;
    }

    public BigDecimal getInitialAmount() {
        return this.initialAmount;
    }

    public void setIsActive(Boolean newValue) {
        this.isActive = newValue;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public boolean isActive() {
        return isActive != null && this.isActive;
    }

    //TODO - move parsing, and length checking to different methods
    public void setMessage(String newValue) {
        message = newValue;

        if (message != null && message.length() > 255) {
            message = message.substring(0, 255);
        }
    }

    public Date getDateToSend() {
        return dateToSend;
    }

    public void setDateToSend(Date dateToSend) {
        this.dateToSend = dateToSend;
    }

    public String getMessage() {
        if (message == null) return "";

        // newlines are allowed in the message, so return them as html line breaks to display correctly
        return message.replaceAll("\r?\n|\r", "<br/>");
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        if (recipientName == null) {
            this.recipientName = "";
            return;
        }
        this.recipientName = recipientName;
        // remove newlines from names
        this.recipientName = this.recipientName.replaceAll("\r?\n|\r", " ");

        if (recipientName.length() > 50) {
            this.recipientName = this.recipientName.substring(0, 50);
        }
    }

    public void setRecipientEmail(String newValue) {
        this.recipientEmail = newValue;
    }

    public String getRecipientEmail() {
        return this.recipientEmail;
    }

    public String getRecipientAddress1() {
        return recipientAddress1;
    }

    public void setRecipientAddress1(String recipientAddress1) {
        this.recipientAddress1 = recipientAddress1;
    }

    public String getRecipientAddressLine1() {
        return getRecipientAddress1();
    }

    public void setRecipientAddressLine1(String recipientAddress1) {
        setRecipientAddress1(recipientAddress1);
    }

    public String getRecipientAddress2() {
        return recipientAddress2;
    }

    public void setRecipientAddress2(String recipientAddress2) {
        this.recipientAddress2 = recipientAddress2;
    }

    public String getRecipientAddressLine2() {
        return getRecipientAddress2();
    }

    public void setRecipientAddressLine2(String recipientAddress2) {
        setRecipientAddress2(recipientAddress2);
    }

    public String getRecipientCity() {
        return recipientCity;
    }

    public void setRecipientCity(String recipientCity) {
        this.recipientCity = recipientCity;
    }

    public String getRecipientState() {
        return recipientState;
    }

    public void setRecipientState(String recipientState) {
        this.recipientState = recipientState;
    }

    public String getRecipientZip() {
        return recipientZip;
    }

    public void setRecipientZip(String recipientZip) {
        this.recipientZip = recipientZip;
    }

    public String getRecipientCountry() {
        return recipientCountry;
    }

    public void setRecipientCountry(String recipientCountry) {
        this.recipientCountry = recipientCountry;
    }

    public void setAmountBalance(BigDecimal newValue) {
        this.amountBalance = newValue;
    }

    public BigDecimal getAmountBalance() {
        return this.amountBalance;
    }

    public Boolean getHasAmountBalance() {
        return !MoneyMath.isZero(getAmountBalance());
    }

    public void setDateActivated(Date newValue) {
        this.dateActivated = newValue;
    }

    public Date getDateActivated() {
        return this.dateActivated;
    }

    public void setCancelDate(Date newValue) {
        this.cancelDate = newValue;
    }

    public Date getCancelDate() {
        return this.cancelDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }


    public void setAmountWeight(Float amountWeight) {
        this.amountWeight = amountWeight;
    }

    public Float getAmountWeight() {
        return amountWeight;
    }

    public Integer getCount() {
        return getId();
    }

    public String getDateCreatedString() {
        return mediumDateFormat.format(getDateCreated());
    }

    public String getDateActivatedString() {
        return mediumDateFormat.format(this.dateActivated);
    }

    public String getCancelDateString() {
        return mediumDateFormat.format(this.cancelDate);
    }


    /**
     * Returns whether a gift certificate is expired at the time this method is called.
     *
     * @return true if the GiftCertificate's expiry is not null and is after the current time
     */
    public boolean isExpired() {
        if (expirationDate == null) return false;

        Calendar now = Calendar.getInstance();
        Calendar expiry = Calendar.getInstance();
        expiry.setTime(expirationDate);

        return now.after(expiry);
    }

    /**
     * Checks whether the gift certificate is valid; error message translation must be done by calling method based on locale and vendor.
     */
    public void checkIsValid(Donor recipient) throws JustGiveException {
        if (isActive && (recipient != null && recipientId != null && !recipient.getId().equals(recipientId))) {
            jgLog.debug("recipient:" + recipient + ", recipientId:" + recipientId);
            throw new JustGiveException("MSG_ACTIVATED_GIFT_CERT");
        } else if (MoneyMath.isZero(getAmountBalance())) {
            throw new JustGiveException("MSG_FULLY_REDEEMED_CERT");
        } else if (isExpired()) {
            throw new JustGiveException("MSG_EXPIRED_GIFT_CERT");
        }
    }

    public String toString() {
        return "GiftCertificate:id=" + getId() +
                ":purchaser=" + purchaser +
                ":recipientId=" + recipientId +
                ":trackingNumber=" + trackingNumber +
                ":initialAmount=" + initialAmount +
                ":amountBalance=" + amountBalance +
                ":dateCreated=" + getDateCreated() +
                ":dateActivated=" + dateActivated +
                ":cancelDate=" + cancelDate +
                ":isActive=" + isActive +
                ":recipientEmail=" + recipientEmail;
    }
}
