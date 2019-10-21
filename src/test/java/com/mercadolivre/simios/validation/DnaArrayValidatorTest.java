package com.mercadolivre.simios.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.validation.ConstraintValidatorContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {
	    "nitrogen.base=A,T,C,G",
	})
public class DnaArrayValidatorTest {

	@Autowired
	private DnaArrayValidator validator;

	@Mock
	private ConstraintValidatorContext context;
	
	
	@Test
	public void validationSuccess() {
		String[] value = createValidArray();
		boolean valid = validator.isValid(value, context);
		assertTrue(valid);
	}

	@Test
	public void validationFailLength() {
		String[] value = createInvalidLengthArray();
		boolean valid = validator.isValid(value, context);
		assertFalse(valid);
	}
	
	@Test
	public void validationFailChar() {
		String[] value = createInvalidCharArray();
		boolean valid = validator.isValid(value, context);
		assertFalse(valid);
	}
	
	
	@Test
	public void validationFailEmpty() {
		String[] value = new String[0];
		boolean valid = validator.isValid(value, context);
		assertFalse(valid);
	}
	
	@Test
	public void validationFailNull() {
		String[] value = null;
		boolean valid = validator.isValid(value, context);
		assertFalse(valid);
	}

	private String[] createInvalidCharArray() {
		final String[] dna = { "ATGCGT", "AXGTGT", "ATATGT", "AAACAT", "TCCCTA", "TCACTG" };
		return dna;
	}

	private String[] createInvalidLengthArray() {
		final String[] dna = { "ATGCGT", "AGTGT", "ATATGT", "AAACAT", "TCCCTA", "TCACTG" };
		return dna;
	}

	private String[] createValidArray() {
		final String[] dna = { "ATGCGT", "AAGTGT", "ATATGT", "AAACAT", "TCCCTA", "TCACTG" };
		return dna;
	}
	
	@Configuration
	@Import(DnaArrayValidator.class)
	static class TestConfig {
		@Bean
		ConstraintValidatorContext constraintValidatorContext() {
			return Mockito.mock(ConstraintValidatorContext.class);
		}
	}

}
