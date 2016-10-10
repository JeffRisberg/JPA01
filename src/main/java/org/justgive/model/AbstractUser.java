package org.justgive.model;

import org.justgive.database.DatedDatabaseItem;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The <i>AbstractUser</i> entity
 *
 * @author Jeffrey Risberg
 * @since April 2014
 */
@MappedSuperclass
public class AbstractUser extends DatedDatabaseItem {

    @Column(name = "login")
    @Size(max = 100)
    //@NotEmpty(groups = RequiredChecks.class)
    @NotNull
    protected String login;

    @Column(name = "password")
    @Size(max = 50)
    //@NotEmpty(groups = {RequiredChecks.class, StrongPasswordCheck.class})
    protected String password;  //password will be hashed

    @Transient
    @NotNull
    protected transient String clearTextPassword;

    @Column
    @Size(max = 100)
    //@NotEmpty(groups = RequiredChecks.class)
    protected String firstName;

    @Column
    @Size(max = 100)
    //@NotEmpty(groups = RequiredChecks.class)
    protected String lastName;

    @Column
    protected Boolean newsletter;

    public String getLogin() {
        return login;
    }

    public void setLogin(String newValue) {
        login = newValue;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newValue) {
        password = newValue;
    }

    /**
     * Sets a new password (in plain text) into the bean. Encrypts
     * incoming plaintext, sets password, sets authenticated to true.
     *
     * @param clearTextPassword new password to be created
     */
    public void setNewPassword(String clearTextPassword) {
        this.clearTextPassword = clearTextPassword;
    }

    public String getClearTextPassword() {
        return clearTextPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String newValue) {
        firstName = newValue;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String newValue) {
        lastName = newValue;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public Boolean getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(Boolean newValue) {
        newsletter = newValue;
    }

    public String toString() {
        return "AbstractUser[login=" + login + ", firstName=" + firstName + ", lastName=" + lastName + "]";
    }
}
