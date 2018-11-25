package com.company.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Jeff Risberg
 * @since 11/3/17
 */

@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
public class Student implements Serializable {
    @Id
    @Column(name = "student_id", unique = true)
    private int id;

    @Column(name = "student_name", nullable = false)
    private String name;

    @Column(name = "student_age", nullable = false)
    private int age;
}
