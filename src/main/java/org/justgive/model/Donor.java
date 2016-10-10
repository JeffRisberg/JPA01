package org.justgive.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * The <i>Donor</i> entity stores firstName, lastName, email
 *
 * @author Jeffrey Risberg
 * @since April 2014
 */
@Entity
@Table(name = "donors")
public class Donor extends AbstractUser {

    public enum Type {
        Guest,
        Registered
    }

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "last_login")
    private Date lastLoginDate;

    @Column(name = "verified")
    private Boolean isVerified = false;

    @Column(name = "address1")
    @Size(max = 100)
    private String address1 = "";

    @Column(name = "address2")
    @Size(max = 100)
    private String address2 = "";

    @Column(name = "city")
    @Size(max = 50)
    private String city = "";

    @Column(name = "state")
    @Size(max = 25)
    private String state = "";

    @Column(name = "zip")
    @Size(max = 15)
    private String zip = "";

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    public Donor() {

    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
