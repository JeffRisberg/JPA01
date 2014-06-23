package org.justgive.models;

import javax.persistence.*;

/**
 * The <i>CharityProgram</i> entity stores
 *
 * @author Jeffrey Risberg
 * @since April 2014
 */
@Entity
@Table(name = "charity_program")
public class CharityProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Charity charity;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public CharityProgram() {
    }

    public CharityProgram(Charity charity, String name, String description) {
        this.charity = charity;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Charity getCharity() {
        return charity;
    }

    public void setCharity(Charity charity) {
        this.charity = charity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CharityProgram that = (CharityProgram) o;

        if (charity != null ? !charity.equals(that.charity) : that.charity != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = charity != null ? charity.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
