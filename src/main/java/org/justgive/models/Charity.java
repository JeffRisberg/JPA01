package org.justgive.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

/**
 * The <i>Charity</i> entity stores name, mission, revenue, and programs for a charity.
 *
 * @author Jeffrey Risberg
 * @since April 2014
 */
@Entity
@Table(name = "charities")
public class Charity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "mission")
    private String mission;

    @Column(name = "revenue")
    private BigDecimal revenue;

    @Column(name = "has_chapters")
    private Boolean hasChapters;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "charity")
    private Set<CharityProgram> charityPrograms;

    public Charity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    public Boolean getHasChapters() {
        return hasChapters;
    }

    public void setHasChapters(Boolean hasChapters) {
        this.hasChapters = hasChapters;
    }

    public Set<CharityProgram> getCharityPrograms() {
        return charityPrograms;
    }

    public void setCharityPrograms(Set<CharityProgram> charityPrograms) {
        this.charityPrograms = charityPrograms;
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
