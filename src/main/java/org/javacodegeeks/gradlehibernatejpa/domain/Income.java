package org.javacodegeeks.gradlehibernatejpa.domain;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.*;

@Entity
@DiscriminatorValue("income")
@Data
@EqualsAndHashCode(callSuper=false)
public class Income extends AccountTransaction {

	public Income(Date date, String description, Double amount) {
		this.date = date;
		this.description = description;
		this.amount = amount;
	}
}
