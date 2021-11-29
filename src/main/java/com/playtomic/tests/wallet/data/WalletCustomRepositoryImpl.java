package com.playtomic.tests.wallet.data;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.playtomic.tests.model.Wallet;

@Repository
public class WalletCustomRepositoryImpl implements WalletCustomRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Wallet incrementWalletBalance(String id, BigDecimal inc) {
		
		Query query = new Query().addCriteria(where("_id").is(id));
		
		Update update = new Update();
		update.inc("balance", inc);
		
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true);
		
		Wallet wallet = mongoTemplate.findAndModify(query, update, options, Wallet.class);
		
		return wallet;
	}

}
