package com.playtomic.tests.wallet;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;

import org.bson.types.Decimal128;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.lang.NonNull;

@Configuration
@Configurable
class MongoConfiguration {
	
	@Bean
	public MongoCustomConversions mongoCustomConversions() {
	    return new MongoCustomConversions(Arrays.asList(
	        new BigDecimalDecimal128Converter(),
	        new Decimal128BigDecimalConverter()
	    ));

	}

	@WritingConverter
	private static class BigDecimalDecimal128Converter implements Converter<BigDecimal, Decimal128> {

	    @Override
	    public Decimal128 convert(@NonNull BigDecimal source) {
	        return new Decimal128(source.round(MathContext.DECIMAL128));
	    }
	}

	@ReadingConverter
	private static class Decimal128BigDecimalConverter implements Converter<Decimal128, BigDecimal> {

	    @Override
	    public BigDecimal convert(@NonNull Decimal128 source) {
	        return source.bigDecimalValue();
	    }

	}


}
