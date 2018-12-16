package com.company.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

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
    private int id;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;
}
