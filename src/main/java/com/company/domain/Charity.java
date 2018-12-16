package com.company.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@DiscriminatorValue("charities")
@Data
public class Charity {

    @Id
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "ein", nullable = false)
    private String ein;

}
