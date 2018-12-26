package com.tmj.tms.utility;

import javax.validation.ConstraintViolation;
import java.util.HashSet;

public class ErrorMessageCreator {

    public static String errorsInRow(HashSet<?> errors, int lineNo) {
        String error = "";
        try {
            for (Object object : errors) {
                ConstraintViolation<Object> objectConstraintViolation = (ConstraintViolation<Object>) object;
                error = error + objectConstraintViolation.getPropertyPath() + " " + objectConstraintViolation.getMessage() + ", ";
            }
            error = error.replaceAll(", $", "") + " at row " + lineNo;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return error;
    }

    public static String errorsInRow(HashSet<?> errors) {
        StringBuilder error = new StringBuilder();
        try {
            for (Object object : errors) {
                ConstraintViolation<Object> objectConstraintViolation = (ConstraintViolation<Object>) object;
                error.append(objectConstraintViolation.getPropertyPath()).append(" ").append(objectConstraintViolation.getMessage()).append(", ");
            }
            error = new StringBuilder(error.toString().replaceAll(", $", ""));
            error = new StringBuilder(error.toString().replaceAll("Seq", ""));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return error.toString();
    }
}
