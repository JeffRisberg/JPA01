package com.company.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "charities")
@NoArgsConstructor
@Data
public class Charity extends AbstractItem {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "ein", nullable = false)
    private String ein;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "charity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Donation> donations = new ArrayList<Donation>();

    public Charity(String name, String ein) {
        this.setId(null);
        this.name = name;
        this.ein = ein;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Charity[name=" + name);
        sb.append(", ein=" + ein);
        sb.append("]");

        return sb.toString();
    }
}
