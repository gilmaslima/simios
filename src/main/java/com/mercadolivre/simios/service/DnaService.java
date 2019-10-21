package com.mercadolivre.simios.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadolivre.simios.helper.DnaHelper;
import com.mercadolivre.simios.model.Dna;
import com.mercadolivre.simios.model.Stats;
import com.mercadolivre.simios.model.StatsResponse;
import com.mercadolivre.simios.repository.DnaRepository;
import com.mercadolivre.simios.repository.StatsCustomRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DnaService {

	@Autowired
	private DnaRepository repository;
	
	@Autowired
	private StatsCustomRepository customRepository;

	@Autowired
	private DnaHelper helper;

	public boolean isSimian(String[] dna) {
		log.info("isSimian started");
		boolean simian = helper.verify(dna);
		Dna entity = Dna.createFromArray(dna);
		entity.setSimian(simian);
		
		save(entity);

		return simian;
	}

	private void save(Dna dna) {
		
		Dna saved = repository.save(dna);
		log.info("Dna saved {}", saved);
	}

	public StatsResponse stats() {
		Stats stats = customRepository.getStats();
		log.info("Getting stats {}", stats);
		if (stats.getTotal() == 0) {
			return StatsResponse.builder().countHumanDna(0L).countSimianDna(0L).ratio(BigDecimal.ZERO.setScale(2))
					.build();
		}
		final Long countMutantDna = stats.getCountMutantDna().longValue();
		final Long countHumanDna = stats.getCountHumanDna().longValue();
		final BigDecimal ratio = countMutantDna == 0 ? BigDecimal.ZERO.setScale(2)
				: countHumanDna == 0 ? new BigDecimal(100).setScale(2)
						: BigDecimal.valueOf(countMutantDna).divide(BigDecimal.valueOf(countHumanDna), 2, RoundingMode.HALF_EVEN);

		return StatsResponse.builder().countHumanDna(countHumanDna).countSimianDna(countMutantDna).ratio(ratio).build();
	}

}
