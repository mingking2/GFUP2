package com.example.gfup2.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Deprecated
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumSubsetValidator.class)
public @interface EnumSubset {
    String message() default "must be any of {subset}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
    String[] subset();
}
