package com.company.domain;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("expense")
@Data
@EqualsAndHashCode(callSuper=false)
public class Expense extends AccountTransaction {

    public Expense(Date date, String description, Double amount) {
        this.date = date;
        this.description = description;
        this.amount = amount;
    }
}
