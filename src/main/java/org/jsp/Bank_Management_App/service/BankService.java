package org.jsp.Bank_Management_App.service;

import java.util.List;
import java.util.Optional;

import org.jsp.Bank_Management_App.dto.ResponseStrucutre;
import org.jsp.Bank_Management_App.entity.Bank;
import org.jsp.Bank_Management_App.exception.IdNotFoundException;
import org.jsp.Bank_Management_App.exception.NoRecordAvailableException;
import org.jsp.Bank_Management_App.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class BankService {
	
	@Autowired
	private BankRepository bankRepository;
	
	// save 1 bank and adress
	public ResponseStrucutre<Bank> saveBank(Bank bank)
	{
		ResponseStrucutre<Bank> res = new ResponseStrucutre<Bank>();
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setMessage("Bank Record Saved Successfully");
		res.setData(bankRepository.save(bank));
		return res;
	}
	//save all bank and address
	public ResponseStrucutre<List<Bank>> saveAllBooks(List<Bank> books)
	{
		ResponseStrucutre<List<Bank>> res = new ResponseStrucutre<>();
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setMessage("All the Bank Details are SAved Successfully");
		res.setData(bankRepository.saveAll(books));
		return res;
	}
	
	// get all the books 
	public ResponseStrucutre<List<Bank>> getAllBooks()
	{
		List<Bank> banks = bankRepository.findAll();
		ResponseStrucutre<List<Bank>> res = new ResponseStrucutre<>();
		if(banks.isEmpty()) {
			throw new NoRecordAvailableException("No Banks are available");
		}else
		{
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("All the Records are available");
			res.setData(banks);
			return res;
		}
	}
	// get bank By Id
	public ResponseStrucutre<Bank> getByID(Integer id)
	{
		Optional<Bank> bank = bankRepository.findById(id);
		ResponseStrucutre<Bank> res = new ResponseStrucutre<Bank>();
		if(bank.isPresent())
		{
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Bank Is Fetched");
			res.setData(bank.get());
			return res;
		}else {
			throw new  IdNotFoundException("No Bank Is Found By this Id ");
		}
	}
	// delete Bank by Id
	public ResponseStrucutre<Bank> deleteById(Integer id)
	{
		Optional<Bank> bank = bankRepository.findById(id);
		ResponseStrucutre<Bank> res = new ResponseStrucutre<Bank>();
		if(bank.isPresent())
		{
			bankRepository.delete(bank.get());
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Record Deleted Successfully");
			res.setData(bank.get());
			return res;
		}else {
			throw new NoRecordAvailableException("No Record BY This Id");
		}
	}
	// getBank By Pagination and Sorting
	
	public ResponseStrucutre<Page<Bank>> getAllBankPaginationAndSorting(int pageNumber , int pageSize , String id)
	{
		Page<Bank> page = bankRepository.findAll(PageRequest.of(pageNumber, pageSize ,Sort.by(id).ascending()));
		ResponseStrucutre<Page<Bank>> res = new ResponseStrucutre<Page<Bank>>();
		if(page.isEmpty())
		{
			throw new NoRecordAvailableException("Page is Empty");
		}else {
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Record of Page Number :" +pageNumber + "are retrived");
			res.setData(page);
			return res;
		}
	}
	
	// get Bank By IFSC CODE
	
	public ResponseStrucutre<Bank> getBankByIfscCode(String ifsc)
	{
		Optional<Bank> opt = bankRepository.findBankByIfsc(ifsc);
		ResponseStrucutre<Bank> res = new ResponseStrucutre<Bank>();
		if(opt.isPresent())
		{
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Bank Is Finded By Using IFSC");
			res.setData(opt.get());
			return res;
		}else {
			throw new NoRecordAvailableException("No Record is available by this ifsc code"+ifsc);
		}
	}
	
	// get Bank by Adress
	
	public ResponseStrucutre<Bank> getBankByAdress(int addressId)
	{
		Optional<Bank> bank = bankRepository.findBankByAddress_AddressId(addressId);
		ResponseStrucutre<Bank> res = new ResponseStrucutre<>();
		if(bank.isPresent())
		{
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Bank By this adress");
			res.setData(bank.get());
			return res;
			
		}else {
			throw new NoRecordAvailableException("No Address is Present By This address id");
		}
	}
	
	// get by city
	
	public ResponseStrucutre<List<Bank>> getBankByCity(String city)
	{
		List<Bank> banks = bankRepository.findBankByAddress_City(city);
		ResponseStrucutre<List<Bank>> res = new ResponseStrucutre<>();
		if(banks.isEmpty())
		{
			throw new NoRecordAvailableException("No Banks In this City");
		}else {
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("No City Is Available");
			res.setData(banks);
			return res;
		}
	}
	
	// getByContactNumber
	
	public ResponseStrucutre<Bank> getBankByContact(long contact)
	{
		Optional<Bank> bank = bankRepository.findBankByContact(contact);
		ResponseStrucutre<Bank> res = new ResponseStrucutre<Bank>();
		if(bank.isPresent())
		{
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Bank Is Present By This COntact Number");
			res.setData(bank.get());
			return res;
		}else {
			throw new NoRecordAvailableException("No Bank Has THis Contact Number");
		}
	}
		
}
