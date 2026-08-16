package org.jsp.Bank_Management_App.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jsp.Bank_Management_App.dto.ResponseStrucutre;
import org.jsp.Bank_Management_App.entity.Address;
import org.jsp.Bank_Management_App.exception.IdNotFoundException;
import org.jsp.Bank_Management_App.exception.NoRecordAvailableException;
import org.jsp.Bank_Management_App.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
	@Autowired
	private AddressRepository addressRepository;
	
	// get address by id
	public ResponseStrucutre<Address> getAddressById(int addressId)
	{
		Optional<Address> bank = addressRepository.findById(addressId);
		ResponseStrucutre<Address> res = new ResponseStrucutre<Address>();
		if(bank.isPresent())
		{
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Address Found By this Id");
			res.setData(bank.get());
			return res;
		}else
		{
			throw new NoRecordAvailableException("No Address found By this id");
		}
	}
	// update address completley
	
	public ResponseStrucutre<String> updateAdrress(Address address)
	{
		ResponseStrucutre<String> res = new ResponseStrucutre<String>();
		if(address.getAddressId()==null)
		{
			res.setStatusCode(HttpStatus.BAD_REQUEST.value());
			res.setMessage("ID must nbe there to update a record");
			res.setData("Failure");
			return res;
		}
		Optional<Address> opt =  addressRepository.findById(address.getAddressId());
		
		if(opt.isPresent())
		{
			addressRepository.save(address);
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Address Record is Updated Successfully");
			res.setData("Success");
			return res;
		}else {
			throw new NoRecordAvailableException("Id not exist");
		}
		
	}
	
	// update partially
	
	public ResponseStrucutre<String> updatePartialAddress(Integer addressId, Map<String, Object> data)
	{
		ResponseStrucutre<String> res = new ResponseStrucutre<String>();
		Optional<Address> opt = addressRepository.findById(addressId);
		if(opt.isPresent())
		{
			Address address = opt.get();
			
			for(Map.Entry<String, Object> entry : data.entrySet())
			{
				String key = entry.getKey();
				Object value = entry.getValue();
				switch(key)
				{
				case "addressId" :
					address.setAddressId((Integer)value);
					break;
				case "street":
					address.setStreet((String)value);
					break;
				case "city":
					address.setCity((String)value);
					break;
				case "state":
					address.setState((String)value);
					break;
				case "pincode":
					address.setPincode((Integer)value);
					break;
				}
			}
			addressRepository.save(address);
			res.setMessage("Address Updated Successfully");
			res.setStatusCode(HttpStatus.OK.value());
			res.setData("Success");
			return res;
		}else {
			throw new IdNotFoundException("id not exist");
		}
	}
	
	// get Address By bank
	
	public ResponseStrucutre<Address> getAddressByBank(Integer bankId)
	{
		Optional<Address> opt = addressRepository.findAddressByBank_BankId(bankId);
		ResponseStrucutre<Address> res = new ResponseStrucutre<Address>();
		if(opt.isPresent())
		{
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Bank Address Goted");
			res.setData(opt.get());
			return res;
		}else
		{
			throw new NoRecordAvailableException("No Address for this Bank");
		}
	}
	
	// getBank By city and Street
	
	public ResponseStrucutre<List<Address>> getAddressByCityAndStreet(String city,String street)
	{
		List<Address> banks = addressRepository.findAddressByCityAndStreet(city, street);
		ResponseStrucutre<List<Address>> res = new ResponseStrucutre<List<Address>>();
		if(banks.isEmpty())
		{
			throw new NoRecordAvailableException("No Bank address present in this city and street");
		}else
		{
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Address is found by city and street");
			res.setData(banks);
			return res;
		}
	}
	
}
