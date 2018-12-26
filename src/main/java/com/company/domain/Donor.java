package com.company.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Jeff Risberg
 * @since 11/3/17
 */

@Entity
@Table(name = "donors")
@Data
@NoArgsConstructor
public class Donor implements Serializable {
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @OneToMany(mappedBy = "donor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Donation> donations = new ArrayList<Donation>();

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Donor[name="+name);
        sb.append("]");

        return sb.toString();
    }
}
