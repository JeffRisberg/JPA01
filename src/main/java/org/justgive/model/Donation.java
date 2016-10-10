package org.justgive.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.justgive.database.DatabaseItem;
import org.justgive.util.MoneyMath;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * The <i>Donation</i> entity stores donor, charity, and amount.
 *
 * @author Jeffrey Risberg
 * @since April 2014
 */
@Entity
@Table(name = "donations")
public class Donation extends DatabaseItem {

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "trans_id")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "orders")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "charityid")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "charities")
    private Charity charity;

    @Column(name = "amount")
    private Float amount = 0f;

    @Column(name = "points")
    private Integer points = 0;

    @Column(name = "points_reward_code")
    private String pointsRewardCode;

    @Column(name = "designation")
    @Size(max = 256)
    private String designation;

    @Column(name = "share_name")
    @NotNull
    private Boolean shareName = false;

    @Column(name = "share_email")
    @NotNull
    private Boolean shareEmail = false;

    @Column(name = "share_address")
    @NotNull
    private Boolean shareAddress = false;

    @Column(name = "isrecurring")
    private Boolean isScheduledRecurring = false;

    @Column(name = "certificateId")
    private String certificateID = null;

    @Column(name = "points_weight")
    private Float pointsWeight = 0f;

    @Column(name = "amount_weight")
    private Float amountWeight = 0f;

    @Column(name = "flat_charge")
    private BigDecimal flatCharge = MoneyMath.ZERO;

    @Column(name = "amount_disbursed")
    private Float amountDisbursed = 0f;

    @Column(name = "processing_charge")
    private BigDecimal processingCharge = MoneyMath.newBigDecimal(0);

    @Column(name = "occasion")
    private String occasion;

    @Column(name = "first_time_donor")
    private Boolean firstTimeDonor = false;

    @Column(name = "sort_order")
    private Integer sortOrder;

    public Donation() {
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Charity getCharity() {
        return charity;
    }

    public void setCharity(Charity charity) {
        this.charity = charity;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getPointsRewardCode() {
        return pointsRewardCode;
    }

    public void setPointsRewardCode(String pointsRewardCode) {
        this.pointsRewardCode = pointsRewardCode;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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

    public Boolean getIsScheduledRecurring() {
        return isScheduledRecurring;
    }

    public void setIsScheduledRecurring(Boolean isScheduledRecurring) {
        this.isScheduledRecurring = isScheduledRecurring;
    }

    public String getCertificateID() {
        return certificateID;
    }

    public void setCertificateID(String certificateID) {
        this.certificateID = certificateID;
    }

    public Float getPointsWeight() {
        return pointsWeight;
    }

    public void setPointsWeight(Float pointsWeight) {
        this.pointsWeight = pointsWeight;
    }

    public Float getAmountWeight() {
        return amountWeight;
    }

    public void setAmountWeight(Float amountWeight) {
        this.amountWeight = amountWeight;
    }

    public BigDecimal getFlatCharge() {
        return flatCharge;
    }

    public void setFlatCharge(BigDecimal flatCharge) {
        this.flatCharge = flatCharge;
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

    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    public Boolean getFirstTimeDonor() {
        return firstTimeDonor;
    }

    public void setFirstTimeDonor(Boolean firstTimeDonor) {
        this.firstTimeDonor = firstTimeDonor;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}
