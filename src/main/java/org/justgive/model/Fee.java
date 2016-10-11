package org.justgive.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.justgive.database.DatabaseItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The Fee entity is created to attach fees to an order or a donation.  Each fee has a type
 * and an amount.
 */
@Entity
@Table(name = "fees")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "fees")
public class Fee extends DatabaseItem {
    private static Logger jgLog = LoggerFactory.getLogger(Fee.class);

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feetypeid")
    private FeeType feeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transactionid")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "orders")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donationid")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "donations")
    private Donation donation;

    @Column(name = "amount")
    private BigDecimal amount;

    /**
     * Default Constructor
     */
    public Fee() {}

    /**
     * Constructor
     */
    public Fee(FeeType feeType) {
        this.feeType = feeType;
    }

    /**
     * Constructor
     */
	public Fee(FeeType feeType, BigDecimal amount) {
		this.feeType = feeType;
		this.amount = amount;
	}

	public FeeType getFeeType() {
        return feeType;
    }

    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Donation getDonation() {
        return donation;
    }

    public void setDonation(Donation donation) {
        this.donation = donation;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
