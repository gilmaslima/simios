package com.mercadolivre.simios.helper;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Scope("prototype")
@Slf4j
public class DnaHelper {

	private String[][] array;

	public boolean verify(String[] dna) {
		array = createArrayOfArray(dna);
		
		int found = findInLine(array);
		if (found == 2) {
			return true;
		}
		found = findInColumn(array, found);
		if (found == 2) {
			return true;
		}
		found = findInDiagonal(array, found);

		if (found == 2) {
			return true;
		}
		found = findInDiagonalReverse(array, found);

		return found == 2;
	}

	private String[][] createArrayOfArray(String[] dna) {
		log.info("Creating array of array");
		int length = dna.length;
		String array[][] = new String[length][length];
		for (int i = 0; i < dna.length; i++) {
			array[i] = dna[i].split("");
		}
		return array;
	}

	private int findInDiagonalReverse(String[][] array, int found) {
		log.info("Diagonal reverse called");
		String previous = null;
		int count = 0;
		int length = array.length;
		for (int i = 0; i < length; i++) {
			String current = null;
			for (int j = length - 1; j >= 0;) {
				if (i > 0 && j - i >= 0) {
					current = array[i][j - i];
				}
				if (previous == null) {
					previous = array[i][j];
					break;
				} else {
					break;
				}

			}
			if (i == 0) {
				continue;
			} else {
				if (current.equals(previous)) {
					++count;
				} else {
					count = 0;
				}
			}

			if (count == 3) {
				found++;
				log.info("Found simian in diagonal reverse");
				if(found == 2) {
					return found;
				}
				count = 0;
			}
			previous = current;
		}

		return found;
	}

	private int findInDiagonal(String[][] array, int found) {
		log.info("Diagonal called");
		String previous = null;
		int count = 0;
		int length = array.length;
		for (int i = 0; i < length; i++) {
			String current = null;
			for (int j = 0; j < length;) {
				if (i > 0 && j + i < length) {
					current = array[i][j + i];
				}
				if (previous == null) {
					previous = array[i][j];
					break;
				} else {
					break;
				}

			}
			if (i == 0) {
				continue;
			} else {
				if (current.equals(previous)) {
					++count;
				} else {
					count = 0;
				}
			}

			if (count == 3) {
				found++;
				log.info("Found simian in diagonal");
				if(found == 2) {
					return found;
				}
				count = 0;
			}
			previous = current;
		}

		return found;
	}

	private int findInColumn(String[][] array, int found) {
		log.info("Column called");
		String previous = null;
		int count = 0;
		int columnNumber = 0;
		int length = array.length;
		for (int i = 0; columnNumber < length; i++) {
			String current = null;
			current = array[i][columnNumber];
			if (previous == null) {
				previous = current;
				continue;
			}

			if (current.equals(previous)) {
				++count;
			} else {
				count = 0;
			}

			if (count == 3) {
				found++;
				log.info("Found simian in Column");
				if(found == 2) {
					return found;
				}
				count = 0;
			}
			previous = current;
			if (i == length - 1 && columnNumber < length - 1) {
				++columnNumber;
				i = -1;
				previous = null;
				count = 0;
			}
			if (columnNumber == length - 1 && i == length - 1) {
				break;
			}
		}

		return found;
	}

	private int findInLine(String[][] array) {
		log.info("Line called");
		int found = 0;
		for (int i = 0; i < array.length; i++) {
			int count = 0;
			String prevLinha = null;
			for (int j = 0; j < array[i].length; j++) {
				String current = array[i][j];
				if (prevLinha == null) {
					prevLinha = current;
					continue;
				} else {
					if (prevLinha.equals(current)) {
						++count;
					} else {
						count = 0;
					}
				}

				if (count == 3) {
					found++;
					log.info("Found simian in Line");
					if(found == 2) {
						return found;
					}
					count = 0;
				}
				prevLinha = current;
			}
		}

		return found;
	}

}
