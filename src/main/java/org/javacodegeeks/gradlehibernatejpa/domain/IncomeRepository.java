package org.javacodegeeks.gradlehibernatejpa.domain;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

public interface IncomeRepository extends CrudRepository<Income, Long> {
	@Transactional
	void deleteByDate(Date date);

	List<Income> findAllByDate(Date date);
}
