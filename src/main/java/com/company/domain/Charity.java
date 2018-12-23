package com.company.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "charities")
@Data
public class Charity {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "ein", nullable = false)
    private String ein;

    @OneToMany(mappedBy = "charity")
    private List<Donation> donations;
}
