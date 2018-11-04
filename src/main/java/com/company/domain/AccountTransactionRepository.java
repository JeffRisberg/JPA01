package com.company.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AccountTransactionRepository extends CrudRepository<AccountTransaction, Long> {
	@Query(value = "SELECT sum(amount) from account_transaction atn where atn.account_type = :account_type", nativeQuery = true)
	Double findTotalByAccountType(@Param("account_type") String account_type);
}