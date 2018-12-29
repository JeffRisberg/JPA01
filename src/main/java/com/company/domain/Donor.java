package com.company.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jeff Risberg
 * @since 11/3/17
 */

@Entity
@Table(name = "donors")
@NoArgsConstructor
@Data
public class Donor extends AbstractItem {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @OneToMany(mappedBy = "donor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Donation> donations = new ArrayList<Donation>();

    public Donor(String name, int age) {
        this.setId(null);
        this.name = name;
        this.age = age;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Donor[name=" + name);
        sb.append(", age=" + age);
        sb.append("]");

        return sb.toString();
    }
}
