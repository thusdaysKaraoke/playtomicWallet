package com.playtomic.tests.wallet.data;

import java.math.BigDecimal;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.playtomic.tests.model.Wallet;

public interface WalletCustomRepository {

    Wallet incrementWalletBalance(String id, BigDecimal inc);

}