package com.playtomic.tests.wallet.service.impl;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.playtomic.tests.model.Wallet;
import com.playtomic.tests.wallet.service.WalletService;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class WalletServiceTest {
	
	@Autowired WalletService walletService;
	
    @Test
    public void createWallet() {
    	Wallet wallet = walletService.createWallet();
    	Assertions.assertTrue(wallet != null && wallet.getId() != null);
    }
    
    @Test
    public void chargeWallet() {
    	BigDecimal amountToAdd = new BigDecimal(999);
    	
    	Wallet wallet = walletService.createWallet();
    	
    	wallet = walletService.refillWallet(wallet.getId(), amountToAdd);

    	Assertions.assertTrue(wallet != null && wallet.getBalance().compareTo(amountToAdd) >= 0);
    }

}
