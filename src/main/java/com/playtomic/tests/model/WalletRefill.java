package com.playtomic.tests.model;

import java.math.BigDecimal;

public class WalletRefill {
	
	String cardNumber;
	BigDecimal value;
	
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}

}
