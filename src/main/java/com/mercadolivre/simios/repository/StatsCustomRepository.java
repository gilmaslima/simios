package com.mercadolivre.simios.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mercadolivre.simios.model.Stats;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class StatsCustomRepository {

	public static final String sql = "SELECT COALESCE(count(dna.id), 0) AS total, COALESCE(count(mut.id), 0) AS countMutantDna, COALESCE(count(hum.id), 0) AS countHumanDna\n"
			+ "FROM dna as dna\n" + "LEFT JOIN dna mut on dna.id = mut.id AND mut.simian = 1\n"
			+ "LEFT JOIN dna hum on dna.id = hum.id AND hum.simian = 0";
	@Autowired
	private EntityManager manager;

	public Stats getStats() {
		log.info("Retrieving data from custom repository");
		Query query = manager.createNativeQuery(sql);
		Object[] result = (Object[]) query.getSingleResult();
		return Stats.createFromObjectArray(result);
	}
}
