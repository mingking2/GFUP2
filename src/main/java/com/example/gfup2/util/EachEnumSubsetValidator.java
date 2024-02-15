package com.example.gfup2.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Deprecated
public class EachEnumSubsetValidator implements ConstraintValidator<EachEnumSubset, List<String>> {
    private Set<String> subset;

    @Override
    public void initialize(EachEnumSubset constraintAnnotation) {
        this.subset = new HashSet<>();
        for(String key : constraintAnnotation.subset()){
            this.subset.add(key.toUpperCase());
        }
    }

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if (value == null) return true;
        for (String v: value){
            if(!this.subset.contains(v.toUpperCase())) return false;
        }
        return true;
    }
}
