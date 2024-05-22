package com.softcorridor.attendance.advice;

import com.softcorridor.attendance.exception.AccessDeniedException;
import com.softcorridor.attendance.exception.DuplicateEntityException;
import com.softcorridor.attendance.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AttendanceExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleValidationException(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        });

        return  errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public Map<String,Object> handleEntityNotFoundException(EntityNotFoundException ex){
        Map<String,Object> errors = new HashMap<>();
        errors.put("code",404);
        errors.put("status","NOT FOUND");
        errors.put("message",ex.getMessage());
        errors.put("data","{}");
        return  errors;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateEntityException.class)
    public Map<String,Object> handleDuplicateEntityException(DuplicateEntityException ex){
        Map<String,Object> errors = new HashMap<>();
        errors.put("code",409);
        errors.put("status","DUPLICATE/CONFLICT");
        errors.put("message",ex.getMessage());
        errors.put("data","{}");
        return  errors;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    public Map<String,Object> handleAccessDeniedException(AccessDeniedException ex){
        Map<String,Object> errors = new HashMap<>();
        errors.put("code",401);
        errors.put("status","ACCESS DENIED!!!");
        errors.put("message",ex.getMessage());
        errors.put("data","{}");
        return  errors;
    }
}
