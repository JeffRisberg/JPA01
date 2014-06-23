package org.justgive.models;

import javax.persistence.*;
import java.util.Set;

/**
 * The <i>Campaign</i> entity stores...
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

    @Column(name = "ein")
    private String ein;

    @Column(name = "executive_director")
    private String executiveDirector;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "charity")
    private Set<CharityProgram> charityPrograms;

    public Charity() {
    }

    public Charity(String name, String ein, String executiveDirector) {
        this.name = name;
        this.ein = ein;
        this.executiveDirector = executiveDirector;
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

    public String getEin() {
        return ein;
    }

    public void setEin(String ein) {
        this.ein = ein;
    }

    public String getExecutiveDirector() {
        return executiveDirector;
    }

    public void setExecutiveDirector(String executiveDirector) {
        this.executiveDirector = executiveDirector;
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
