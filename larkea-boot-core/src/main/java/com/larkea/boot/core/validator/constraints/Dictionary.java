package com.larkea.boot.core.validator.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.larkea.boot.core.validator.DictionaryValidator;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Constraint(validatedBy = DictionaryValidator.class)
public @interface Dictionary {

	String[] value() default {};

	String message() default "dict value is not found";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
