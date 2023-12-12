package com.example.bookstore.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String[] fieldNames;

    @Override
    public void initialize(FieldMatch fieldMatch) {
        fieldNames = fieldMatch.fields();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        try {
            List<String> fieldValues = new ArrayList<>();
            Class<?> clazz = o.getClass();
            for (String field : fieldNames) {
                Field declaredField = clazz.getDeclaredField(field);
                declaredField.setAccessible(true);
                fieldValues.add((String) declaredField.get(o));
            }

            if (fieldValues.stream().distinct().count() > 1) {
                return false;
            }
            return true;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Fields don't match or wrong field names!");
        }

    }
}
