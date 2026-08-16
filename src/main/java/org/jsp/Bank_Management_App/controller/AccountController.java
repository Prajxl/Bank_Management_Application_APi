package org.jsp.Bank_Management_App.controller;

import java.util.List;

import org.jsp.Bank_Management_App.dto.ResponseStrucutre;
import org.jsp.Bank_Management_App.entity.Account;
import org.jsp.Bank_Management_App.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/account")
@RestController
public class AccountController {
	@Autowired
	private AccountService accountService;
	
	
	@PostMapping
	public ResponseEntity<ResponseStrucutre<Account>> saveAccount(@RequestBody Account  account)
	{
		return new ResponseEntity<>(accountService.saveAccount(account),HttpStatus.CREATED);
	}
	
	@PostMapping("/all")
	public ResponseEntity<ResponseStrucutre<List<Account>>> saveAllAccount(@RequestBody List<Account>  accounts)
	{
		return new ResponseEntity<>(accountService.saveAllAccount(accounts),HttpStatus.CREATED);
	}
	
	@GetMapping("/{accountId}")
	public ResponseEntity<ResponseStrucutre<Account>> getBankById(@PathVariable Integer accountId)
	{
		return new ResponseEntity<>(accountService.getAccountById(accountId),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStrucutre<Account>> deleteByID(@PathVariable Integer id)
	{
		return new ResponseEntity<>(accountService.deleteAccountById(id),HttpStatus.OK);
	}
	
	@PutMapping("deposit/{accountNumber}/{amount}")
	public ResponseEntity<ResponseStrucutre<String>> depositeAmount(@PathVariable String accountNumber,@PathVariable Double amount )
	{
		return new ResponseEntity<>(accountService.depositeAmount(accountNumber, amount),HttpStatus.OK);
	}
	
	@PutMapping("withdraw/{accountNumber}/{amount}")
	public ResponseEntity<ResponseStrucutre<String>> withdrawAmount(@PathVariable String accountNumber,@PathVariable Double amount )
	{
		return new ResponseEntity<>(accountService.withdrawAmount(accountNumber, amount),HttpStatus.OK);
	}
	@PutMapping("transaction/{senderaccountNumber}/{recieveraccountNumber}/{amount}")
	public ResponseEntity<ResponseStrucutre<String>> transactionAmount(@PathVariable String senderaccountNumber,@PathVariable String recieveraccountNumber,@PathVariable Double amount )
	{
		return new ResponseEntity<>(accountService.TransferAmount(senderaccountNumber, recieveraccountNumber, amount),HttpStatus.OK);
	}
	
	@GetMapping("bank/{bankId}")
	public ResponseEntity<ResponseStrucutre<List<Account>>> getAccountByBank(@PathVariable Integer bankId)
	{
		return new ResponseEntity<>(accountService.getAccountByBank(bankId),HttpStatus.OK);
	}
	
	@GetMapping("/{accountNumber}")
	public ResponseEntity<ResponseStrucutre<Account>> getAccountByBank(@PathVariable String accountNumber)
	{
		return new ResponseEntity<>(accountService.getAccountByAccountNumber(accountNumber),HttpStatus.OK);
	}
	
	@GetMapping("GreaterThan/{value}")
	public ResponseEntity<ResponseStrucutre<List<Account>>> getAccountGreaterThan(@PathVariable Double value)
	{
		return new ResponseEntity<>(accountService.getAccountByBalanceGreaterThan(value),HttpStatus.OK);
	}
	
	@GetMapping("/sortname")
	public ResponseEntity<ResponseStrucutre<List<Account>>> getAccountsBySorting() {
	    return new ResponseEntity<>(accountService.getAccountsBySorting(),HttpStatus.OK);
	}
	
	
}
