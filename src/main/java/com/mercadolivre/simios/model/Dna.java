package com.mercadolivre.simios.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dna implements Serializable {

	private static final long serialVersionUID = 6764359727519709758L;

	@Id
	private Long id;

	private String dna;

	private LocalDateTime lastUpDate;
	
	private boolean simian;

	public static Dna createFromArray(String[] array) {
		String dna = "";
		for (int i = 0; i < array.length; i++) {
			dna += array[i];
		}
		final Long id = UUID.nameUUIDFromBytes(dna.getBytes()).getMostSignificantBits();

		return Dna.builder().id(id).dna(dna).lastUpDate(LocalDateTime.now()).build();

	}

}
