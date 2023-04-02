package com.server.notesapp.annotation;

import com.server.notesapp.validation.SecuredPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SecuredPasswordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SecuredPassword {

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "Password isn't strong enough";
}
