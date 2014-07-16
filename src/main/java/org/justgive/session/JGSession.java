package org.justgive.session;

import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;
import org.justgive.properties.JustGiveProperties;
import org.justgive.properties.PropertyException;

import javax.validation.constraints.NotNull;
import java.util.Calendar;

public class JGSession {
    private static Logger jgLog = LoggerFactory.getLogger(JGSession.class);

    private static Integer SESSION_EXPIRED_MINUTES = 30;
    private static Integer SESSION_EXPIRED_HOURS = 24;
    private static Integer SESSION_EXPIRED_DAYS = 3;
    private static Integer SESSION_INACTIVE_MINUTES = 30;

    @NotNull
    private String jSessionId;
    @NotNull
    private String returnId;
    private Integer donorId;
    private Integer affiliateId;
    private Integer vendorId;
    private boolean isAuthenticated;

    //private Donor donor;
    //private Affiliate affiliate;
    //private Vendor vendor;

    /**
     * Default null constructor
     */
    public JGSession() {
        //nada
    }
    //GETTERS AND SETTERS

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

    public void setUserId(org.justgive.models.User user) {
    }

    public void setAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public boolean isDonorAuthenticated() {
        return isAuthenticated && getDonorId() != null;
    }

    public boolean isAffiliateAuthenticated() {
        return isAuthenticated && getAffiliateId() != null;
    }

    /**
     * Checks whether this session is expired.  Defined as 1/2 hour since last hit.
     *
     * @return boolean
     */
    public boolean isExpired() {
        Calendar now = Calendar.getInstance();

        Calendar lastHit = Calendar.getInstance();
        //lastHit.setTime(getLastUpdated());
        int expireDuration;
        try {
            expireDuration = JustGiveProperties.getInt("session.expired.minutes");
            lastHit.add(Calendar.MINUTE, expireDuration);
            jgLog.debug("JGSession expiration timeout in minutes: " + expireDuration);
        } catch (PropertyException e) {
            jgLog.debug(e.getMessage());
            try {
                expireDuration = JustGiveProperties.getInt("session.expired.hours");
                lastHit.add(Calendar.HOUR, expireDuration);
                jgLog.debug("JGSession expiration timeout in hours: " + expireDuration);
            } catch (PropertyException e1) {
                jgLog.debug(e1.getMessage());
                try {
                    expireDuration = JustGiveProperties.getInt("session.expired.days");
                    lastHit.add(Calendar.DATE, expireDuration);
                    jgLog.debug("JGSession expiration timeout in days: " + expireDuration);
                } catch (PropertyException e2) {
                    jgLog.debug(e2.getMessage());
                    lastHit.add(Calendar.MINUTE, SESSION_EXPIRED_MINUTES);
                    jgLog.debug("JGSession set to default expiration timeout in days: " + SESSION_EXPIRED_MINUTES);
                }
            }
        }
        boolean isExpired = now.after(lastHit);
        jgLog.debug("isExpired? " + isExpired);
        return isExpired;
    }

    /**
     * Checks whether this session is expired.  Defined as 1/2 hour since last hit.
     *
     * @return boolean
     */
    public boolean isActive() {
        Calendar now = Calendar.getInstance();

        Calendar lastHit = Calendar.getInstance();
        //lastHit.setTime(getLastUpdated());
        int inactiveDuration;
        try {
            inactiveDuration = JustGiveProperties.getInt("session.inactive.minutes");
            jgLog.debug("JGSession inactivity timeout in minutes: " + inactiveDuration);
        } catch (PropertyException e) {
            jgLog.debug(e.getMessage());
            inactiveDuration = SESSION_INACTIVE_MINUTES;
            jgLog.debug("JGSession set to default inactivity timeout in minutes: " + inactiveDuration);
        }
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
