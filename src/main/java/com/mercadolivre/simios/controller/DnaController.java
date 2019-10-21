package com.mercadolivre.simios.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolivre.simios.model.DnaRequest;
import com.mercadolivre.simios.model.SimianResponse;
import com.mercadolivre.simios.model.StatsResponse;
import com.mercadolivre.simios.service.DnaService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Validated
@Slf4j
public class DnaController {

	@Autowired
	private DnaService service;

	@PostMapping(path = "/simian", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity isSimian(@Valid @RequestBody DnaRequest request) {
		try {
			log.info("Init request simian endpoint {}", request);
			SimianResponse response = new SimianResponse(service.isSimian(request.getDna()));
			log.info("Simian found: {}", response.isSimian());
			return new ResponseEntity<SimianResponse>(response, HttpStatus.OK);
			
		} catch (Exception e) {
			log.error("unexpected error", e);
			return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/stats", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<StatsResponse> stats() {
		try {
			log.info("Init request stats endpoint");
			ResponseEntity<StatsResponse> response = ResponseEntity.ok(service.stats());
			log.info("End of request stats endpoint {}", response.getBody());
			return response;
		} catch (Exception e) {
			log.error("unexpected error", e);
			return new ResponseEntity<StatsResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/")
	public ResponseEntity<Integer> heathCheck(){
		return new ResponseEntity<Integer>(HttpStatus.OK);
	}
}
