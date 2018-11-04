package org.javacodegeeks.gradlehibernatejpa.domain;

import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type")
@Data
public abstract class AccountTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	protected Date date;
	protected String description;
	protected Double amount;
}
