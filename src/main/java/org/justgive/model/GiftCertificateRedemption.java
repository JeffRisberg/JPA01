package org.justgive.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.justgive.database.DatedDatabaseItem;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * A GiftCertificateRedemption is a payment activity, that records how much has been
 * redeemed from a giftCertificate.  It has a reference to the GC, and to the Order
 * within which the redemption is being performed.
 */
@Entity
@Table(name = "gc_redemptions")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "gc_redemptions")
public class GiftCertificateRedemption extends DatedDatabaseItem {
    public static Integer GIFT_PROFILE = 3672;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transactionid")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "orders")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "giftcertid")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "giftcertificates")
    public GiftCertificate giftCertificate;

    @Column(name = "amountredeemed")
    private BigDecimal amountRedeemed;

    @Column(name = "redeemer_firstname")
    private String redeemerFirstName;

    @Column(name = "redeemer_lastname")
    private String redeemerLastName;

    @Column(name = "redeemer_email")
    private String redeemerEmail;

    /**
     * Default Constructor
     */
    public GiftCertificateRedemption() {
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public GiftCertificate getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificate giftCertificate) {
        this.giftCertificate = giftCertificate;
    }

    public void setAmountRedeemed(BigDecimal amountRedeemed) {
        this.amountRedeemed = amountRedeemed;
    }

    public BigDecimal getAmountRedeemed() {
        return this.amountRedeemed;
    }

    public String getRedeemerFirstName() {
        return redeemerFirstName;
    }

    public void setRedeemerFirstName(String redeemerFirstName) {
        this.redeemerFirstName = redeemerFirstName;
    }

    public String getRedeemerLastName() {
        return redeemerLastName;
    }

    public void setRedeemerLastName(String redeemerLastName) {
        this.redeemerLastName = redeemerLastName;
    }

    public String getRedeemerEmail() {
        return redeemerEmail;
    }

    public void setRedeemerEmail(String redeemerEmail) {
        this.redeemerEmail = redeemerEmail;
    }
}


