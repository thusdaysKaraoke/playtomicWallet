package com.playtomic.tests.wallet.service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.playtomic.tests.model.Wallet;
import com.playtomic.tests.wallet.api.WalletController;
import com.playtomic.tests.wallet.data.WalletRepository;

@Service
public class WalletService {
	private Logger log = LoggerFactory.getLogger(WalletController.class);

	@Autowired
	WalletRepository repository;

	
	public List<String> getWallets(Integer page, Integer size, String sort) {
		log.info("Retrieving wallets");
		
		if (sort == null) {
			sort = "id";
		};		
		if (page == null) {
			page = 0;
		}		
		if (size == null) {
			size = 10;
		}

		Pageable searchOptions = PageRequest.of(page, size, Sort.by(sort));
		Page<Wallet> search = repository.findAll(searchOptions);
		
		List<String> results = new LinkedList<String>();
		search.forEach(wallet -> results.add(wallet.getId()));
		
		return results;
	}
	
	public Wallet getWallet(String id){
		log.info("Retrieving wallet "+id);
		
		return repository.findById(id).orElse(null);
	}
		
	public Wallet createWallet() {
		log.info("Creating new wallet");
		
		return repository.insert(new Wallet());
	}
	
	public Wallet updateWallet(Wallet wallet) {
		log.info("Updating wallet "+wallet.getId());
		
		return repository.save(wallet);
	}
	
	public Wallet refillWallet(String id, BigDecimal balanceToAdd) {
		log.info("Refilling wallet "+id);

		return repository.incrementWalletBalance(id, balanceToAdd);
	}



}
