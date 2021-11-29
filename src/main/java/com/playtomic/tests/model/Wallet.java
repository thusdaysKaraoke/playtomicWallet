package com.playtomic.tests.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;  

@Document(collection = "wallets")
public class Wallet {
	
	public Wallet(){
		this.balance = new BigDecimal(0);
	}

	@Id
	private String id;
	
	//@Field(targetType = FieldType.DECIMAL128)
	private BigDecimal balance;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
