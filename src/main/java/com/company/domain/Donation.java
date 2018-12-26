package com.company.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "donations")
@Data
@NoArgsConstructor
public class Donation {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "created_at", nullable = false)
    private Date created_at;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_id")
    private Donor donor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "charity_id")
    private Charity charity;
}
