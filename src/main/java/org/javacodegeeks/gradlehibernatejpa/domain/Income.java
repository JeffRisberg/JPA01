package org.javacodegeeks.gradlehibernatejpa.domain;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("income")
@Data
@Getter
@Setter
@NoArgsConstructor
public class Income extends AccountTransaction {

	public Income(Date date, String description, Double amount) {
		this.date = date;
		this.description = description;
		this.amount = amount;
	}
}
