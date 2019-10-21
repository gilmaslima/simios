package com.mercadolivre.simios.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mercadolivre.simios.model.Dna;

@Repository
public interface DnaRepository extends CrudRepository<Dna, Long> {

}
