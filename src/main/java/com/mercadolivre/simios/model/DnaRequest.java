package com.mercadolivre.simios.model;



import javax.validation.constraints.NotNull;

import com.mercadolivre.simios.validation.DnaConstraint;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DnaRequest {
	
	@NotNull(message="The property dna is required")
	@DnaConstraint
	@ApiParam(required=true)
	private String[] dna;

}
