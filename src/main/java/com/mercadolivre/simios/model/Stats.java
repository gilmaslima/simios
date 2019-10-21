package com.mercadolivre.simios.model;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Stats {

	private Integer total;

	private Integer countMutantDna;

	private Integer countHumanDna;

	public static Stats createFromObjectArray(Object[] result) {
		log.info("Creatting Stats from query result {}", result);
		Stats stats = new Stats(0,0,0);
		if (result != null && result.length == 3) {
			stats.total = ((BigInteger) result[0]).intValue();
			stats.countMutantDna = ((BigInteger) result[1]).intValue();
			stats.countHumanDna = ((BigInteger) result[2]).intValue();
		}
		return stats;
	}

}
