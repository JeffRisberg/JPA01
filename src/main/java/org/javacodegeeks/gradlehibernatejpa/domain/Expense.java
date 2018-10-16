package org.javacodegeeks.gradlehibernatejpa.domain;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("expense")
@Getter
@Setter
@NoArgsConstructor
public class Expense extends AccountTransaction {

	public Expense(Date date, String description, Double amount) {
		this.date = date;
		this.description = description;
		this.amount = amount;
	}
}
