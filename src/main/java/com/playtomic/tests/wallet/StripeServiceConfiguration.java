package com.playtomic.tests.wallet;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.playtomic.tests.wallet.service.StripeService;

@Configuration
public class StripeServiceConfiguration {
	@Value("${stripe.simulator.charges-uri}") URI chargesUri;
	@Value("${stripe.simulator.refunds-uri}") URI refundsUri;
	
	@Bean
	public StripeService autoStripeService() {
		return new StripeService(chargesUri, refundsUri, new RestTemplateBuilder()); 
	}
}
