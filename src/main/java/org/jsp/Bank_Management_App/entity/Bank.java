package org.jsp.Bank_Management_App.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Bank {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bankId;
	private String bankName;
	@Column(unique = true)
	private String ifsc;
	private String branchName;
	@Column(unique = true)
	private long contact;
	
	@JsonIgnore
	@OneToMany(mappedBy = "bank",cascade = CascadeType.ALL)
	private List<Account> accounts;
	
	@OneToOne(cascade = CascadeType.ALL )
	@JoinColumn
	private Address address;
	
}
