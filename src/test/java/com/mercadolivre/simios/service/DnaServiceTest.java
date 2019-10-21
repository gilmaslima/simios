package com.mercadolivre.simios.service;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.mercadolivre.simios.helper.DnaHelper;
import com.mercadolivre.simios.model.Dna;
import com.mercadolivre.simios.model.Stats;
import com.mercadolivre.simios.repository.DnaRepository;
import com.mercadolivre.simios.repository.StatsCustomRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DnaServiceTest {

	@Autowired
	private DnaService service;

	@MockBean
	private DnaHelper helper;
	
	@MockBean
	private DnaRepository repository;
	
	@MockBean
	private StatsCustomRepository customRepository;
	
	@Before
	public void init() {
		
	}
	
	@Test
	public void testSimianLineBasedSuccess() {
		String[] dna = createDna();
		Mockito.when(helper.verify(dna)).thenReturn(true);
		
		Dna entity = new Dna();
		Mockito.when(repository.save(entity)).thenReturn(entity);
		
		assertTrue(service.isSimian(dna));
		Mockito.verify(helper).verify(dna);
		
	}
	
	@Test
	public void testStatsCountZero() {
		
		Object[] result = new Object[0];
		Stats stats = Stats.createFromObjectArray(result);
		Mockito.when(customRepository.getStats()).thenReturn(stats);
		
		assertTrue(0L == service.stats().getCountHumanDna());
		assertTrue(0L == service.stats().getCountSimianDna());
		assertTrue(BigDecimal.ZERO.setScale(2).equals(service.stats().getRatio()));
	}

	
	@Test
	public void testStatsHumanZero() {
		
		Stats stats = new Stats(6, 6, 0);
		Mockito.when(customRepository.getStats()).thenReturn(stats);
		
		assertTrue(0L == service.stats().getCountHumanDna());
		assertTrue(6L == service.stats().getCountSimianDna());
		BigDecimal ratio = BigDecimal.valueOf(100.00).setScale(2);
		assertTrue(ratio.equals(service.stats().getRatio()));

	}
	
	@Test
	public void testStatsMutantZero() {
		
		Stats stats = new Stats(6, 0, 6);
		Mockito.when(customRepository.getStats()).thenReturn(stats);
		
		assertTrue(6L == service.stats().getCountHumanDna());
		assertTrue(0L == service.stats().getCountSimianDna());
		assertTrue(BigDecimal.ZERO.setScale(2).equals(service.stats().getRatio()));
	}
	
	private String[] createDna() {
		final String[] dna = { "ATGCGT", "AAGTGT", "ATATGT", "AAACAT", "TCCCTA", "TCACTG" };
		return dna;
	}
	
	@Configuration
	@Import(DnaService.class)
	static class TestConfig {
		@Bean
		DnaRepository dnaRepository() {
			return Mockito.mock(DnaRepository.class);
		}

		@Bean
		StatsCustomRepository statsCustomRepository() {
			return Mockito.mock(StatsCustomRepository.class);
		}
	}
	
}
