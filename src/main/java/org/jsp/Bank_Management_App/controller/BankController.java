package org.jsp.Bank_Management_App.controller;

import java.util.List;

import org.jsp.Bank_Management_App.dto.ResponseStrucutre;
import org.jsp.Bank_Management_App.entity.Bank;
import org.jsp.Bank_Management_App.repository.BankRepository;
import org.jsp.Bank_Management_App.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/bank")
@RestController
public class BankController {
	
	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private BankService bankService;
	
	
	// save single bank
	@PostMapping
	public ResponseEntity<ResponseStrucutre<Bank>> saveBank(@RequestBody Bank bank)
	{
		return new ResponseEntity<>(bankService.saveBank(bank),HttpStatus.CREATED);
	}
	// save all the bank
	@PostMapping("/all")
	public ResponseEntity<ResponseStrucutre<List<Bank>>> saveAllBank(@RequestBody List<Bank> banks)
	{
		return new ResponseEntity<>(bankService.saveAllBanks(banks),HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStrucutre<List<Bank>>> getAllBanks()
	{
		return new ResponseEntity<>(bankService.getAllBooks(),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStrucutre<Bank>> getBankById(@PathVariable Integer id)
	{
		return new ResponseEntity<>(bankService.getByID(id),HttpStatus.OK);
	}
	
	// delete a record
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStrucutre<Bank>> deleteByID(@PathVariable Integer id)
	{
		return new ResponseEntity<>(bankService.deleteById(id),HttpStatus.OK);
	}
	
	@GetMapping("pagination/{pageNumber}/{pageSize}/{fieldName}")
	public ResponseEntity<ResponseStrucutre<Page<Bank>>> getAllBankPaginationandSorting(
			@PathVariable int pageNumber , @PathVariable int pageSize , @PathVariable String fieldName){
		
		return new ResponseEntity<>(bankService.getAllBankPaginationAndSorting(pageNumber, pageSize,fieldName),HttpStatus.OK);
	}
	
	@GetMapping("ifsc/{ifsc}")
	public ResponseEntity<ResponseStrucutre<Bank>> getBankByIfscCode(@PathVariable String ifsc)
	{
		return new ResponseEntity<>(bankService.getBankByIfscCode(ifsc),HttpStatus.OK);
	}
	
	@GetMapping("address/{addressId}")
	public ResponseEntity<ResponseStrucutre<Bank>> getBankByAddress(
			@PathVariable int addressId)
	{
		return new ResponseEntity<>(bankService.getBankByAdress(addressId),HttpStatus.OK);
	}
	
	@GetMapping("city/{city}")
	public ResponseEntity<ResponseStrucutre<List<Bank>>> getAllBanksByAddress(@PathVariable   String city)
	{
		return new ResponseEntity<>(bankService.getBankByCity(city),HttpStatus.OK);
	}
	
	@GetMapping("contact/{contact}")
	public ResponseEntity<ResponseStrucutre<Bank>> getBankByContact(@PathVariable long contact)
	{
		return new ResponseEntity<>(bankService.getBankByContact(contact),HttpStatus.OK);
	}
}
