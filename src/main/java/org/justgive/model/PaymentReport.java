package org.justgive.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.justgive.database.DatedDatabaseItem;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Jeff Risberg
 * @since 06/03/16
 */
@Entity
@Table(name = "payment_report")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "payment_reports")
public class PaymentReport extends DatedDatabaseItem {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "charity_id")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "charities")
    private Charity charity;

    @OneToMany(mappedBy = "paymentReport", fetch = FetchType.LAZY)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "donations")
    private List<Donation> donations = new ArrayList<>();

    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "check_number")
    private String checkNumber;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "payee_name")
    private String payeeName;

    /**
     * Default Constructor
     */
    public PaymentReport() {
    }

    public void setCharity(Charity charity) {
        this.charity = charity;
    }

    public Charity getCharity() {
        return charity;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getPayeeName() {
        return payeeName;
    }
}
