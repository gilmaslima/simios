package com.mercadolivre.simios.repository;

import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.mercadolivre.simios.model.Stats;
import com.mercadolivre.simios.service.DnaService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatsCustomRepositoryTest {

	@Autowired
	private StatsCustomRepository customRepository;

	@MockBean
	private EntityManager manager;

	@Mock
	private Query query;

	@Test
	public void successTest() {
		Mockito.when(manager.createNativeQuery(StatsCustomRepository.sql)).thenReturn(query);
		Mockito.when(query.getSingleResult()).thenReturn(new Object[0]);
		Stats stats = customRepository.getStats();
		assertTrue(0L == stats.getTotal());
	}

	@Configuration
	@Import(StatsCustomRepository.class)
	static class TestConfig {
		@Bean
		EntityManager entityManager() {
			return Mockito.mock(EntityManager.class);
		}
	}

}
