package com.example.customvalidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PostalCodeValidator implements ConstraintValidator<PostalCode, Integer> {

    @Override
    public boolean isValid(Integer postalCode, ConstraintValidatorContext context) {
        if (postalCode >=100000  && postalCode <= 999999) {
            return true;
        } else {
            return false;
        }
    }
}
