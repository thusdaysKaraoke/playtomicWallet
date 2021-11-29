package com.playtomic.tests.wallet.data;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.playtomic.tests.model.Wallet;  
public interface WalletRepository extends MongoRepository<Wallet, String>, WalletCustomRepository
{  
}  