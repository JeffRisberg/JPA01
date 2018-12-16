package com.company.domain;

import java.util.Date;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "donations")
@Data
@NoArgsConstructor
public class Donation {

	@Id
	@Column(name = "id", unique = true)
	private int id;

	@Column(name = "created_at", nullable = false)
	private Date created_at;

	@Column(name = "amount", nullable = false)
	private Double amount;

}
