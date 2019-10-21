package com.mercadolivre.simios.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({ "count_simian_dna", "count_human_dna", "ratio" })
public class StatsResponse {
	
	@JsonProperty("count_simian_dna")
	private Long countSimianDna;
	
	@JsonProperty("count_human_dna")
	private Long countHumanDna;
	
	private BigDecimal ratio;
	
	
}
