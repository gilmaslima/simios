package com.mercadolivre.simios.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.mercadolivre.simios.model.DnaRequest;
import com.mercadolivre.simios.model.SimianResponse;
import com.mercadolivre.simios.model.StatsResponse;
import com.mercadolivre.simios.service.DnaService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DnaControllerTest {

	@Autowired
	private DnaController controller;

	@MockBean
	private DnaService service;

	@Test
	public void postWithEmptyData() {
		try {
			String[] array = new String[0];
			controller.isSimian(new DnaRequest(array));
		} catch (Exception e) {
			assertTrue(e instanceof ConstraintViolationException);
		}
	}

	@Test
	public void postErrorHandler() {
		String[] array = createMutantDna();
		Mockito.when(service.isSimian(array)).thenThrow(NullPointerException.class);
		ResponseEntity<Integer> response = controller.isSimian(new DnaRequest(array));
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
	}

	@Test
	public void getErrorHandler() {
		Mockito.when(service.stats()).thenThrow(NullPointerException.class);
		ResponseEntity<StatsResponse> response = controller.stats();
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
	}

	@Test
	public void postWithValidData() {
		String[] array = createMutantDna();
		Mockito.when(service.isSimian(array)).thenReturn(true);
		ResponseEntity<SimianResponse> response = controller.isSimian(new DnaRequest(array));
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
		assertTrue(response.getBody().isSimian());

	}

	@Test
	public void postWithHumanData() {
		String[] array = createHumanDna();
		Mockito.when(service.isSimian(array)).thenReturn(false);
		ResponseEntity<SimianResponse> response = controller.isSimian(new DnaRequest(array));
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
		assertFalse(response.getBody().isSimian());
	}

	@Test
	public void getStats() {
		StatsResponse stats = StatsResponse.builder().countHumanDna(0L).countSimianDna(0L)
				.ratio(BigDecimal.ZERO.setScale(2)).build();
		Mockito.when(service.stats()).thenReturn(stats);
		ResponseEntity<StatsResponse> response = controller.stats();
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
		assertEquals(stats, response.getBody());
	}

	private String[] createMutantDna() {
		final String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "GAAAAG", "CCCCTA", "TCACTG" };
		return dna;
	}

	private String[] createHumanDna() {
		final String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "GCAAAG", "TCCCTA", "TCACTG" };
		return dna;
	}

	@Configuration
	@Import(DnaController.class)
	static class TestConfig {
		@Bean
		DnaService dnaService() {
			return Mockito.mock(DnaService.class);
		}
	}

}
