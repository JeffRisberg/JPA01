package org.justgive.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

/**
 * The JGSession entity is the main entity used for storing session state, where the session state
 * consists of the vendor, affiliate, donor, the tracking object, the impressions list, and other information.
 *
 * @author various
 * @since 2007
 */
@Entity
@Table(name = "sessions")
public class JGSession {
    private static Logger jgLog = LoggerFactory.getLogger(JGSession.class);

    private static Integer SESSION_EXPIRED_MINUTES = 30;
    private static Integer SESSION_EXPIRED_HOURS = 24;
    private static Integer SESSION_EXPIRED_DAYS = 3;
    private static Integer SESSION_INACTIVE_MINUTES = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq_gen")
    @SequenceGenerator(name = "users_seq_gen", sequenceName = "users_id_seq")
    private Long id;

    @Column(name = "jsessionid")
    @NotNull
    private String jSessionId;

    @Column(name = "returnid")
    @NotNull
    private String returnId;

    @Column(name = "donorid")
    private Integer donorId;

    @Column(name = "affiliateid")
    private Integer affiliateId;

    @Column(name = "vendorid")
    private Integer vendorId;

    @Column(name = "is_authenticated")
    private boolean isAuthenticated;

    /**
     * Default null constructor
     */
    public JGSession() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setJSessionId(String newValue) {
        jSessionId = newValue;
    }

    public String getJSessionId() {
        return jSessionId;
    }

    public void setReturnId(String newValue) {
        returnId = newValue;
    }

    public String getReturnId() {
        return returnId;
    }

    public void setDonorId(Integer donorId) {
        this.donorId = donorId;
    }

    public Integer getDonorId() {
        return donorId;
    }

    public boolean hasDonorId() {
        return donorId != null;
    }

    public void setAffiliateId(Integer affiliateId) {
        this.affiliateId = affiliateId;
    }

    public Integer getAffiliateId() {
        return affiliateId;
    }

    public boolean hasAffiliateId() {
        return affiliateId != null;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public void setAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Transient
    public boolean isDonorAuthenticated() {
        return isAuthenticated && getDonorId() != null;
    }

    @Transient
    public boolean isAffiliateAuthenticated() {
        return isAuthenticated && getAffiliateId() != null;
    }

    /**
     * Checks whether this session is expired.  Defined as 1/2 hour since last hit.
     *
     * @return boolean
     */
    @Transient
    public boolean isExpired() {
        Calendar now = Calendar.getInstance();

        Calendar lastHit = Calendar.getInstance();
        //lastHit.setTime(getLastUpdated());
        int expireDuration;
        lastHit.add(Calendar.MINUTE, SESSION_EXPIRED_MINUTES);

        boolean isExpired = now.after(lastHit);
        jgLog.debug("isExpired? " + isExpired);
        return isExpired;
    }

    /**
     * Checks whether this session is expired.  Defined as 1/2 hour since last hit.
     *
     * @return boolean
     */
    @Transient
    public boolean isActive() {
        Calendar now = Calendar.getInstance();

        Calendar lastHit = Calendar.getInstance();
        //lastHit.setTime(getLastUpdated());
        int inactiveDuration = SESSION_INACTIVE_MINUTES;

        lastHit.add(Calendar.MINUTE, inactiveDuration);

        boolean isActive = now.before(lastHit);
        jgLog.debug("isActive? " + isActive);
        return isActive;
    }

    /**
     * Set a session attribute for a given name
     *
     * @param name      The attribute name
     * @param attribute The attribute Object
     */
    public void setAttribute(String name, Object attribute) {
        if (name == null) {
            throw new IllegalArgumentException("Null session attribute name");
        }

        // code deleted here
    }

    /**
     * Returns the attribute Object value associated with the attribute name.
     *
     * @param name The attribute name
     * @return The attribute Object value, null if not found
     */
    public Object getAttribute(String name) {
        // Not found, return null
        return null;
    }
}
