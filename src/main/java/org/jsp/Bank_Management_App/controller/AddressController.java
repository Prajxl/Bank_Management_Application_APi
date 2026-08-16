package org.jsp.Bank_Management_App.controller;

import java.util.List;
import java.util.Map;

import org.jsp.Bank_Management_App.dto.ResponseStrucutre;
import org.jsp.Bank_Management_App.entity.Address;
import org.jsp.Bank_Management_App.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/address")
@RestController
public class AddressController {
	@Autowired
	private AddressService addressService;
	
	@GetMapping("/{addressId}")
	public ResponseEntity<ResponseStrucutre<Address>> getAdressById(@PathVariable int addressId)
	{
		return new ResponseEntity<>(addressService.getAddressById(addressId),HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<ResponseStrucutre<String>> updateAdress(@RequestBody Address address)
	{
		return new ResponseEntity<>(addressService.updateAdrress(address),HttpStatus.OK);
	}
	
	@PatchMapping("/{addressId}")
	public ResponseEntity<ResponseStrucutre<String>> updatePartialAddress(@PathVariable Integer addressId , @RequestBody Map<String, Object> data)
	{
		return new ResponseEntity<>(addressService.updatePartialAddress(addressId, data),HttpStatus.OK);
	}
	
	@GetMapping("bank/{bankId}")
	public ResponseEntity<ResponseStrucutre<Address>> getAdressByBank(@PathVariable Integer bankId)
	{
		return new ResponseEntity<>(addressService.getAddressByBank(bankId),HttpStatus.OK);
	}
	
	@GetMapping("/{city}/{street}")
	public ResponseEntity<ResponseStrucutre<List<Address>>> getBankByCityAndStreet(@PathVariable String city,@PathVariable String street)
	{
		return new ResponseEntity<>(addressService.getAddressByCityAndStreet(city, street),HttpStatus.OK);
	}
}
