package com.company.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "charities")
@Data
public class Charity extends AbstractItem {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "ein", nullable = false)
    private String ein;

    @OneToMany(mappedBy = "charity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Donation> donations = new ArrayList<Donation>();

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Charity[name=" + name);
        sb.append(", ein=" + ein);
        sb.append("]");

        return sb.toString();
    }
}
