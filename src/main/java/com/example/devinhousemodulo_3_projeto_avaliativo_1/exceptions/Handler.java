package com.example.devinhousemodulo_3_projeto_avaliativo_1.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class Handler {

    //  MUITO BOM ARTIGO - https://reflectoring.io/bean-validation-with-spring-boot/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request ) {

        ValidationErrorResponse error = new ValidationErrorResponse();

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.getCampos().add(
                    new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setTimestamp(System.currentTimeMillis());
        error.setPath(request.getRequestURI());
        error.setError("Erros de Validação");

        return error;
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<StandardError> businessRule(DateTimeParseException e, HttpServletRequest request){
        StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI(), "Error de Transformação de formato de Data","Transformação de Formato de Data Não Suportada");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err); // OU INTERNAL_SERVER_ERROR
    }

    // https://www.toptal.com/java/spring-boot-rest-api-error-handling
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        String error = "Malformed JSON request";
        StandardError standardError = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI(), error, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<StandardError> businessRule(BusinessRuleException e, HttpServletRequest request){
        StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.CONFLICT.value(),request.getRequestURI(),
                "Regra de negócio nao respeitada", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest request){
        StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),request.getRequestURI(),
                "Falha De Busca de Entidade", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StandardError> entityNotFound(MethodArgumentTypeMismatchException e, HttpServletRequest request){
        String errorString = "Parametro " + e.getValue() + " diferente do que se esperava " + e.getRequiredType();
        StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),request.getRequestURI(),
                "Erro Tipo de parametro", errorString);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

}