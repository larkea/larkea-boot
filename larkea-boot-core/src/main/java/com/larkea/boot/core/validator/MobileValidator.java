package com.larkea.boot.core.validator;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.larkea.boot.core.validator.constraints.Mobile;

/**
 * Check China mobile format
 */
public class MobileValidator implements ConstraintValidator<Mobile, String> {

	private final static Pattern pattern = Pattern.compile("^1[3-9]\\d{9}$");

	@Override
	public boolean isValid(String value,
			ConstraintValidatorContext constraintValidatorContext) {
		if (value == null) {
			return true;
		}

		return pattern.matcher(value).matches();
	}
}
