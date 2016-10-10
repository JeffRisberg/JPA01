package org.justgive.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.justgive.database.DatedDatabaseItem;
import org.justgive.model.Order;
import org.justgive.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Gift is a transfer of donations, wrapped with a name and a message.
 * <p/>
 * A Gift is a associated with an Order.
 * <p/>
 * A Gift may also have a NoteCard associated with it.
 *
 * @author Curtis
 * @since 2007
 */
@Entity
@Table(name = "gift")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "gifts")
public class Gift extends DatedDatabaseItem {
    private static Logger jgLog = LoggerFactory.getLogger(Gift.class);

    @OneToMany(mappedBy = "gift")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "donations")
    private List<Donation> donations = new ArrayList<>();

    @Column(name = "memorial_name")
    @Size(max = 256)
    private String memorialName;

    @Column(name = "recipient_name")
    @Size(max = 256)
    private String recipientName;

    @Column(name = "recipient_email")
    @Size(max = 256)
    private String recipientEmail;

    @Column(name = "message")
    @Size(max = 1024)
    private String message;

    @Column(name = "show_amount")
    private Boolean showAmount = true;

    /**
     * Default Constructor
     */
    public Gift() {}

    public String getMemorialName() {
        return memorialName;
    }

    public void setMemorialName(String memorialName) {
        this.memorialName = memorialName;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getMessage() {
        return message == null ? "" : message.trim();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getShowAmount() {
        return showAmount;
    }

    public void setShowAmount(Boolean showAmount) {
        this.showAmount = showAmount;
    }

    public boolean hasRecipient() {
        return !(StringUtil.isEmpty(getRecipientName()) && StringUtil.isEmpty(getMemorialName()));
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }

    public boolean hasDonation(Donation donation) {
        boolean hasDonation = donations != null && donations.contains(donation);
        jgLog.debug("Gift has donation? " + hasDonation);
        return hasDonation;
    }

    public boolean hasDonations() {
        return (donations != null && !donations.isEmpty());
    }
}
