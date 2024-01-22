package com.example.gfup2.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.Set;

@Deprecated
public class EnumSubsetValidator implements ConstraintValidator<EnumSubset, String> {
    private Set<String> subset;

    @Override
    public void initialize(EnumSubset constraintAnnotation){
        this.subset = new HashSet<>();
        for(String key: constraintAnnotation.subset()){
            this.subset.add(key.toUpperCase());
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return subset.contains(value.toUpperCase());
    }
}
