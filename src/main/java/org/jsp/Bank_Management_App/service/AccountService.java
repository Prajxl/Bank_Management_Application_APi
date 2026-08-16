package org.jsp.Bank_Management_App.service;

import java.util.List;
import java.util.Optional;

import org.jsp.Bank_Management_App.dto.ResponseStrucutre;
import org.jsp.Bank_Management_App.entity.Account;
import org.jsp.Bank_Management_App.exception.IdNotFoundException;
import org.jsp.Bank_Management_App.exception.NoRecordAvailableException;
import org.jsp.Bank_Management_App.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
	@Autowired
	private AccountRepository accountRepository;
	
	// create an account
	public ResponseStrucutre<Account> saveAccount(Account account)
	{
		ResponseStrucutre<Account> res = new ResponseStrucutre<Account>();
		res.setMessage("One Account got saved");
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setData(accountRepository.save(account));
		return res;
	}
	
	// create all account
		public ResponseStrucutre<List<Account>> saveAllAccount(List<Account> accounts)
		{
			ResponseStrucutre<List<Account>> res = new ResponseStrucutre<>();
			res.setMessage("all Accounts got saved");
			res.setStatusCode(HttpStatus.CREATED.value());
			res.setData(accountRepository.saveAll(accounts));
			return res;
		}
	
	// getAccounnt by id
		
		public ResponseStrucutre<Account> getAccountById(Integer accountId)
		{
			Optional<Account> opt = accountRepository.findById(accountId);
			ResponseStrucutre<Account> res = new ResponseStrucutre<Account>();
			if(opt.isPresent())
			{
				res.setStatusCode(HttpStatus.OK.value());
				res.setMessage("Account Fetched");
				res.setData(opt.get());
				return res;
			}else
			{
				throw new IdNotFoundException("No Record found By this id");
			}
		}
	
		// delete Account
		
		public ResponseStrucutre<Account> deleteAccountById(Integer accountId)
		{
			Optional<Account> opt = accountRepository.findById(accountId);
			ResponseStrucutre<Account> res = new ResponseStrucutre<Account>();
			if(opt.isPresent())
			{
				accountRepository.delete(opt.get());
				res.setStatusCode(HttpStatus.OK.value());
				res.setMessage("Account Fetched");
				res.setData(opt.get());
				return res;
			}
			else
			{
				throw new IdNotFoundException("No Record found By this id");
			}
		}
		
		// deposite Amount
		public ResponseStrucutre<String> depositeAmount(String accountNumber,Double amount)
		{
			Optional<Account> opt = accountRepository.findAccountByAccountNumber(accountNumber);
			ResponseStrucutre<String> res = new ResponseStrucutre<>();
			if(opt.isEmpty())
			{
				throw new NoRecordAvailableException("No Account is there by this AccountNumber");
			}else if(amount<=0)
			{
				res.setStatusCode(HttpStatus.BAD_REQUEST.value());
				res.setMessage("InSufficient amount");
				res.setData("Amount Not Deposited");
				return res;
			}
			Account account = opt.get();
			account.setBalance(amount + account.getBalance());
			accountRepository.save(account);
			res.setMessage("Amount deposited succefully");
			res.setData("Amount "+ amount + "Succefully Deposited to " +account.getAccountNumber() + " The Total Balance is : "+account.getBalance());
			res.setStatusCode(HttpStatus.OK.value());
			return res;
		}
		
		// withdraw ammount 
		
		public ResponseStrucutre<String> withdrawAmount(String accountNumber,Double amount)
		{
			Optional<Account> opt = accountRepository.findAccountByAccountNumber(accountNumber);
			ResponseStrucutre<String> res = new ResponseStrucutre<String>();
			Account account = opt.get();
			if(opt.isEmpty())
			{
				throw new NoRecordAvailableException("No Account is there by this AccountNumber");
			}else if(amount>=account.getBalance())
			{
				res.setMessage("Insufficient Balance"+account.getBalance());
				res.setStatusCode(HttpStatus.BAD_REQUEST.value());
				res.setData("Failure");
				return res;
			}
			account.setBalance(account.getBalance() - amount);
			accountRepository.save(account);
			res.setMessage("Amount "+amount +" is withdrawn available Balance is "+account.getBalance());
			res.setStatusCode(HttpStatus.OK.value());
			res.setData("Succcessfull");
			return res;
				
		}
		
		// Transfer Amount 
		
		public ResponseStrucutre<String> TransferAmount(String senderaccountNumber , String recieveraccountNumber , Double amount)
		{
		 	Optional<Account> opt1 = accountRepository.findAccountByAccountNumber(senderaccountNumber);
		 	Optional<Account> opt2 = accountRepository.findAccountByAccountNumber(recieveraccountNumber);
		 	ResponseStrucutre<String> res = new ResponseStrucutre<String>();
		 	if(opt1.isEmpty() || opt2.isEmpty())
		 	{
		 		if(opt1.isEmpty())
		 		{
		 			res.setMessage("Sender Account Number Not available");
		 			res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		 			res.setData("Failure");
		 			return res;
		 		}
		 		if(opt2.isEmpty())
		 		{
		 			res.setMessage("Reciever Account Number Not available");
		 			res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		 			res.setData("Failure");
		 			return res;
		 		}
		 	}
		 	if(senderaccountNumber.equals(recieveraccountNumber))
		 	{
		 		res.setMessage("Sender and Reciever Account Number Must Not be same");
	 			res.setStatusCode(HttpStatus.BAD_REQUEST.value());
	 			res.setData("Failure");
	 			return res;
		 	}
		 	Account sender = opt1.get();
		 	Account reciever = opt2.get();
		 	if(amount>sender.getBalance())
		 	{
		 		res.setMessage("Insufcient Balance in Sender "+amount);
	 			res.setStatusCode(HttpStatus.BAD_REQUEST.value());
	 			res.setData("Failure");
	 			return res;
		 	}
		 	sender.setBalance(sender.getBalance() - amount);
		 	reciever.setBalance(reciever.getBalance() + amount );
		 	accountRepository.save(sender);
		 	accountRepository.save(reciever);
		 	res.setMessage("Amount Transferred Successfully "+reciever.getBalance());
		 	res.setStatusCode(HttpStatus.OK.value());
		 	res.setData("Success");
		 	return res;
		}
		
		// get account by bank
		
		public ResponseStrucutre<List<Account>> getAccountByBank(Integer bankId)
		{
			List<Account> accounts = accountRepository.findAccountByBank_BankId(bankId);
			ResponseStrucutre<List<Account>> res = new ResponseStrucutre<>();
			if(accounts.isEmpty())
			{
				throw new IdNotFoundException("No Account in By this bank id");
			}else {
				res.setStatusCode(HttpStatus.OK.value());
				res.setMessage("Account Found In the Bank");
				res.setData(accounts);
				return res;
			}
		}
		
		// get Account By Account Number
		public ResponseStrucutre<Account> getAccountByAccountNumber(String accountNumber)
		{
			Optional<Account> opt = accountRepository.findAccountByAccountNumber(accountNumber);
			ResponseStrucutre<Account> res = new ResponseStrucutre<Account>();
			if(opt.isPresent()) {
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Account Found In the Bank");
			res.setData(opt.get());
			return res;
			}else {
				throw new NoRecordAvailableException("There is no account by this account number");
			}
		}
		
		// get account by balance greater than a value
		
		public ResponseStrucutre<List<Account>> getAccountByBalanceGreaterThan(Double value)
		{
			List<Account> accounts = accountRepository.findAccountByBalanceGreaterThan(value);
			ResponseStrucutre<List<Account>> res = new ResponseStrucutre<>();
			if(accounts.isEmpty()) {
				throw new NoRecordAvailableException("There is no account by this account number");
			}else {
				res.setStatusCode(HttpStatus.OK.value());
				res.setMessage("Accounts Greater than "+value);
				res.setData(accounts);
				return res;
			}
		}
		
		// get Account By Sorting
		
		public ResponseStrucutre<List<Account>> getAccountsBySorting() {
		    List<Account> accounts =accountRepository.findAll(Sort.by("accountHolderName").ascending());
		    ResponseStrucutre<List<Account>> res = new ResponseStrucutre<>();
		    if (accounts.isEmpty()) {
		        throw new NoRecordAvailableException("No accounts available");
		    }
		    res.setStatusCode(HttpStatus.OK.value());
		    res.setMessage("Accounts sorted by accountHolder name");
		    res.setData(accounts);
		    return res;
		}
}
