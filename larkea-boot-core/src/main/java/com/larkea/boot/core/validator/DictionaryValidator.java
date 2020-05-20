package com.larkea.boot.core.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.larkea.boot.core.validator.constraints.Dictionary;

/**
 * 检查值是否在字典中
 */
public class DictionaryValidator implements ConstraintValidator<Dictionary, Integer> {

	private String[] values;

	@Override
	public void initialize(Dictionary constraintAnnotation) {
		this.values = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(Integer value,
			ConstraintValidatorContext constraintValidatorContext) {
		boolean isValid = false;
		if (value == null) {
			return true;
		}
		for (int i = 0; i < values.length; i++) {
			if (values[i].equals(String.valueOf(value))) {
				isValid = true;
				break;
			}
		}
		return isValid;
	}
}
