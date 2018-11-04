package com.company.domain;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {
	@Transactional
	void deleteByDate(Date date);

	List<Expense> findAllByDate(Date date);
}
