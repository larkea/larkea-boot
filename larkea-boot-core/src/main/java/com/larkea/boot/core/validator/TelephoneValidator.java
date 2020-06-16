package com.larkea.boot.core.validator;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.larkea.boot.core.validator.constraints.Telephone;

/**
 * Check China telephone number format
 */
public class TelephoneValidator implements ConstraintValidator<Telephone, String> {

    private final static Pattern pattern = Pattern.compile("^0\\d{2,3}-\\d{7,8}(-\\d{1,4})?$");

    @Override
    public boolean isValid(String value,
                           ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }

        return pattern.matcher(value).matches();
    }
}
