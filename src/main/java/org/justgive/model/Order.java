package org.justgive.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.justgive.database.DatedDatabaseItem;
import org.justgive.util.MoneyMath;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * The <i>Order</i> entity.
 *
 * @author Jeffrey Risberg
 * @since April 2014
 */
@Entity
@Table(name = "transactions")
public class Order extends DatedDatabaseItem {

    @Column(name = "completed_date")
    private Date completedDate;

    @Column(name = "external_id")
    private String externalId;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "source")
    private OrderSource orderSource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendorid")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "vendors")
    private Vendor vendor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_id")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "donors")
    private Donor donor;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    private List<Donation> donations = new ArrayList<>();

    @Column(name = "amount")
    private BigDecimal amount = MoneyMath.ZERO;

    @Column(name = "amount_charged")
    private BigDecimal amountCharged = MoneyMath.ZERO;

    @Column(name = "processing_charges")
    private BigDecimal processingCharges = MoneyMath.ZERO;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("initialAmount DESC")
    private List<GiftCertificate> giftCertsToBuy = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    protected Set<GiftCertificateRedemption> giftCertRedemptions = new HashSet<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fee> fees = new ArrayList<>();

    @Column(name = "reference_code")
    private String referenceCode;

    @Column(name = "payment_code")
    private String payPalReferenceCode;

    public Order() {
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
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

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Donor getDonor() {
        return donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
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

    public BigDecimal getProcessingCharges() {
        return processingCharges;
    }

    public void setProcessingCharges(BigDecimal processingCharges) {
        this.processingCharges = processingCharges;
    }

    public List<GiftCertificate> getGiftCertsToBuy() {
        return giftCertsToBuy;
    }

    public Set<GiftCertificateRedemption> getGiftCertRedemptions() {
        return giftCertRedemptions;
    }

    public List<Fee> getFees() {
        return fees;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public String getPayPalReferenceCode() {
        return payPalReferenceCode;
    }
}
