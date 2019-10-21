package com.mercadolivre.simios.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DnaArrayValidator implements ConstraintValidator<DnaConstraint, String[]> {

	@Value("#{'${nitrogen.base}'.split(',')}")
	private List<String> nitrogenBase;

	@Override
	public boolean isValid(String[] value, ConstraintValidatorContext context) {
		int length = value == null ? 0 : value.length;
		for (int i = 0; i < length; i++) {
			String[] chars = value[i].split("");
			if (chars.length != length) {
				log.info("Invalid array length {}", length);
				return false;
			}
			for (int j = 0; j < length; j++) {
				if (!nitrogenBase.contains(chars[j])) {
					log.info("Invalid character received {}", chars[j]);
					return false;
				}
			}
		}

		return length > 1;
	}

}
