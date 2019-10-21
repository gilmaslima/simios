package com.mercadolivre.simios.helper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DnaHelperTest {

	@Autowired
	private DnaHelper helper;

	@Test
	public void testSimianLineBasedSuccess() {
		String[] dna = createDnaLineBased();
		assertTrue(helper.verify(dna));
	}

	@Test
	public void testSimianLineBasedOneFoundFail() {
		String[] dna = createDnaLineBasedOneFoundFail();
		assertFalse(helper.verify(dna));
	}

	@Test
	public void testSimianColumnBasedSuccess() {
		String[] dna = createDnaColumnBased();
		assertTrue(helper.verify(dna));
	}

	@Test
	public void testSimianColumnBasedOneFoundFail() {
		String[] dna = createDnaColumnBasedOneFoundFail();
		assertFalse(helper.verify(dna));
	}

	@Test
	public void testSimianDiagonalBasedSuccess() {
		String[] dna = createDnaDiagonalBased();
		assertTrue(helper.verify(dna));
	}

	@Test
	public void testSimianDiagonalReverseBasedSuccess() {
		String[] dna = createDnaDiagonalReverseBased();
		assertTrue(helper.verify(dna));
	}

	@Test
	public void testSimianDiagonalReverseBasedOneFoundFail() {
		String[] dna = createDnaDiagonalReverseOneFoundFail();
		assertFalse(helper.verify(dna));
	}

	private String[] createDnaDiagonalReverseOneFoundFail() {
		final String[] dna = { "ATGCGT", "CAGTTG", "ATATGA", "AATCGT", "CCCGTA", "TCACTG" };
		return dna;
	}

	private String[] createDnaDiagonalReverseBased() {
		final String[] dna = { "ATGCGT", "CAGTTG", "ATATGA", "AATAAT", "CCCATA", "TCACTG" };
		return dna;
	}

	@Test
	public void testSimianDiagonalBasedOneFoundFail() {
		String[] dna = createDnaDiagonalOneFoundFail();
		assertFalse(helper.verify(dna));
	}

	private String[] createDnaDiagonalOneFoundFail() {
		final String[] dna = { "ATGCGT", "CAGTTG", "ATATGT", "AACAAT", "CCCATA", "TCACTG" };
		return dna;
	}

	private String[] createDnaDiagonalBased() {
		final String[] dna = { "ATGCGT", "AAGTTG", "ATATGT", "AATAAT", "TCCATA", "TCACTG" };
		return dna;
	}

	private String[] createDnaColumnBased() {
		final String[] dna = { "ATGCGT", "AAGTGT", "ATATGT", "AAACAT", "TCCCTA", "TCACTG" };
		return dna;
	}

	private String[] createDnaColumnBasedOneFoundFail() {
		final String[] dna = { "ATGCGA", "AAGTGC", "ATATGT", "AAACAG", "TCCCTA", "TCACTG" };
		return dna;
	}

	private String[] createDnaLineBasedOneFoundFail() {
		final String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "GAACAG", "CCCCTA", "TCACTG" };
		return dna;
	}

	private String[] createDnaLineBased() {
		final String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "GAAAAG", "CCCCTA", "TCACTG" };
		return dna;
	}

	@Configuration
	@Import(DnaHelper.class)
	static class TestConfig {

	}

}
