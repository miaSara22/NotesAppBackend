package com.server.notesapp.validation;

import com.server.notesapp.annotation.FieldsMatch;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldsMatchValidator implements ConstraintValidator<FieldsMatch, Object> {

    private String fieldOne;
    private String fieldTwo;

    @Override
    public void initialize(FieldsMatch constraintAnnotation) {

        this.fieldOne = constraintAnnotation.fieldOne();
        this.fieldTwo = constraintAnnotation.fieldTwo();

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        Object fieldOneValue = new BeanWrapperImpl(value).getPropertyValue(fieldOne);
        Object fieldTwoValue = new BeanWrapperImpl(value).getPropertyValue(fieldTwo);

        if (fieldOneValue != null && fieldTwoValue != null)
            return fieldOneValue.equals(fieldTwoValue);
        else
            return false;

    }
}