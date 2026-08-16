package org.jsp.Bank_Management_App.repository;

import java.util.List;
import java.util.Optional;

import org.jsp.Bank_Management_App.entity.Address;
import org.jsp.Bank_Management_App.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer>{
	public Optional<Address> findAddressByBank_BankId(Integer bankId);
	public List<Address> findAddressByCityAndStreet(String city , String street);
}
