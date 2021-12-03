package com.playtomic.tests.wallet.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playtomic.tests.model.Wallet;
import com.playtomic.tests.model.WalletRefill;
import com.playtomic.tests.wallet.service.StripeService;
import com.playtomic.tests.wallet.service.WalletService;

@RestController
@RequestMapping("/wallet")
public class WalletController {
	private Logger log = LoggerFactory.getLogger(WalletController.class);

	@Autowired
	WalletService walletService;
	
	@Autowired
    @Qualifier("autoStripeService")
	StripeService stripeService;

	@GetMapping("")
	ResponseEntity<List<String>> getWalletsId(@RequestParam(name = "index", defaultValue = "0") Integer index,
			@RequestParam(name = "size", defaultValue = "10") Integer size, @RequestParam(name = "sort", defaultValue = "id") String sort) {
		log.info(String.format("HTTP Request: retrieving wallets for parameters { index: %d, size: %d, sort: %s}",
				index, size, sort));

		try {
			List<String> wallets = walletService.getWallets(index, size, sort);
			return new ResponseEntity<List<String>>(wallets, HttpStatus.OK);

		} catch (Exception e) {
			log.error(String.format("Unable to retrieving wallets: %s", e.getMessage()));
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping
	ResponseEntity<Wallet> insertWallet(Wallet wallet) {
		log.info("HTTP Request: wallet creation");

		try {

			Wallet newWallet = walletService.createWallet();
			return new ResponseEntity<Wallet>(newWallet, HttpStatus.CREATED);

		} catch (Exception e) {
			log.error(String.format("Unable to create wallet: %s", e.getMessage()));
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/{id}")
	ResponseEntity<Wallet> getWallet(@PathVariable("id") String id) {
		log.info("HTTP Request: retrieving wallet " + id);

		try {

			Wallet wallet = walletService.getWallet(id);

			if (wallet == null) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<Wallet>(wallet, HttpStatus.OK);

		} catch (Exception e) {
			log.error(String.format("Unable to retrieving wallet: %s", e.getMessage()));
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/{id}/charge")
	ResponseEntity<Wallet> chargeWallet(@PathVariable("id") String id, @RequestBody WalletRefill refill) {
		log.info("HTTP Request: refilling wallet " + id);

		try {

			stripeService.charge(refill.getCardNumber(), refill.getValue());
		} catch (Exception e) {
			log.error(String.format("Stripe service error: %s", e.getMessage()));
			return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
		}

		try {
			Wallet wallet = walletService.refillWallet(id, refill.getValue());

			if (wallet == null) {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
			
			return new ResponseEntity<Wallet>(wallet, HttpStatus.ACCEPTED);
			
		} catch (Exception e) {
			log.error(String.format("Unable to updating wallet: %s", e.getMessage()));
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
