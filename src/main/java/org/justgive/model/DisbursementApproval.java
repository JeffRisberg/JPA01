package org.justgive.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.justgive.database.DatedDatabaseItem;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Jeff Risberg
 * @since 08/12/15
 */
@Entity
@Table(name = "disbursement_approval")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "disbursement_approval")
public class DisbursementApproval extends DatedDatabaseItem {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "vendors")
    private Vendor vendor;

    @Column(name = "approval_date")
    private Date approvalDate;

    @Column(name = "donation_count")
    private Integer donationCount;

    @Column(name = "charity_count")
    private Integer charityCount;

    @Column(name = "donation_amount")
    private BigDecimal donationAmount;

    @Column(name = "check_run_start_date")
    private Date checkRunStartDate;

    @Column(name = "check_run_end_date")
    private Date checkRunEndDate;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Transient
    private PaymentReport paymentReport;

    /**
     * Constructor
     */
    public DisbursementApproval() {
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Integer getDonationCount() {
        return donationCount;
    }

    public void setDonationCount(Integer donationCount) {
        this.donationCount = donationCount;
    }

    public Integer getCharityCount() {
        return charityCount;
    }

    public void setCharityCount(Integer charityCount) {
        this.charityCount = charityCount;
    }

    public BigDecimal getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(BigDecimal donationAmount) {
        this.donationAmount = donationAmount;
    }

    public Date getCheckRunStartDate() {
        return checkRunStartDate;
    }

    public void setCheckRunStartDate(Date checkRunStartDate) {
        this.checkRunStartDate = checkRunStartDate;
    }

    public Date getCheckRunEndDate() {
        return checkRunEndDate;
    }

    public void setCheckRunEndDate(Date checkRunEndDate) {
        this.checkRunEndDate = checkRunEndDate;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public PaymentReport getPaymentReport() {
        return paymentReport;
    }

    public void setPaymentReport(PaymentReport paymentReport) {
        this.paymentReport = paymentReport;
    }
}
