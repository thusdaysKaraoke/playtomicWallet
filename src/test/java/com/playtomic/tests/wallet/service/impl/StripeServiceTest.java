package com.playtomic.tests.wallet.service.impl;


import java.math.BigDecimal;
import java.net.URI;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.context.ActiveProfiles;

import com.playtomic.tests.wallet.service.StripeAmountTooSmallException;
import com.playtomic.tests.wallet.service.StripeService;
import com.playtomic.tests.wallet.service.StripeServiceException;

/**
 * This test is failing with the current implementation.
 *
 * How would you test this?
 */
@SpringBootTest
public class StripeServiceTest {
	
	@Value("${stripe.simulator.charges-uri}") URI chargesUri;
	@Value("${stripe.simulator.refunds-uri}") URI refundsUri;

	
    @Test
    public void test_exception() {
    	StripeService s = new StripeService(chargesUri, refundsUri, new RestTemplateBuilder());
        Assertions.assertThrows(StripeAmountTooSmallException.class, () -> {
            s.charge("4242 4242 4242 4242", new BigDecimal(5));
        });
    }

    @Test
    public void test_ok() throws StripeServiceException {
    	StripeService s = new StripeService(chargesUri, refundsUri, new RestTemplateBuilder());
        s.charge("4242 4242 4242 4242", new BigDecimal(15));
    }
}
