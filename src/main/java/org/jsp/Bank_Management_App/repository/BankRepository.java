package org.jsp.Bank_Management_App.repository;

import java.util.List;
import java.util.Optional;

import org.jsp.Bank_Management_App.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Integer>{
	public Optional<Bank> findBankByIfsc(String ifsc);
	public Optional<Bank> findBankByAddress_AddressId(int addressId);
	public List<Bank> findBankByAddress_City(String city);
	public Optional<Bank> findBankByContact(long contact);
}