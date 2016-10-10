package org.justgive.model;

import org.justgive.database.DatedDatabaseItem;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * The <i>Charity</i> entity stores name, mission, revenue, and programs for a charity.
 *
 * @author Jeffrey Risberg
 * @since April 2014
 */
@Entity
@Table(name = "charities")
public class Charity extends DatedDatabaseItem {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "source")
    private CharitySource source;

    @Column(name = "charityname")
    private String name;

    @Column(name = "external_id")
    private String externalId;

    @NotNull
    @Column(name = "ein")
    private String ein;

    @NotNull
    @Size(min = 2, max = 4096, message = "The mission must be at least two chars long.")
    @Column(name = "mission")
    private String mission;

    public Charity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Charity charity = (Charity) o;

        if (name != null ? !name.equals(charity.name) : charity.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
