package org.jsp.Bank_Management_App.repository;

import java.util.List;
import java.util.Optional;

import org.jsp.Bank_Management_App.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
	public Optional<Account> findAccountByAccountNumber(String accountNumber);
	public List<Account> findAccountByBank_BankId(Integer bankId);
	public List<Account> findAccountByBalanceGreaterThan(Double value);
}
