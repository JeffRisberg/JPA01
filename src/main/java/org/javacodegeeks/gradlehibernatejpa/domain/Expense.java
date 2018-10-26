package org.javacodegeeks.gradlehibernatejpa.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("expense")
@Data
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
