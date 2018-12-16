package com.company.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue("charities")
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

}
