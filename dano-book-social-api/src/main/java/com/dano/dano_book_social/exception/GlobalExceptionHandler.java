package com.dano.dano_book_social.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.mail.MessagingException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException ex) {
        var response = ExceptionResponse.builder()
                                        .errorCode(ErrorCodes.ACCOUNT_LOCKED.getCode())
                                        .error(ex.getMessage())
                                        .errorDescrtiption(ErrorCodes.ACCOUNT_LOCKED.getDescription())
                                        .build();

        return ResponseEntity.status(ErrorCodes.ACCOUNT_LOCKED.getHttpStatus()).body(response);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException ex) {
        var response = ExceptionResponse.builder()
                                        .errorCode(ErrorCodes.ACCOUNT_DISABLED.getCode())
                                        .error(ex.getMessage())
                                        .errorDescrtiption(ErrorCodes.ACCOUNT_DISABLED.getDescription())
                                        .build();

        return ResponseEntity.status(ErrorCodes.ACCOUNT_DISABLED.getHttpStatus()).body(response);
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException ex) {
        var response = ExceptionResponse.builder()
                                        .errorCode(ErrorCodes.BAD_CREDENTIALS.getCode())
                                        .error(ex.getMessage())
                                        .errorDescrtiption(ErrorCodes.BAD_CREDENTIALS.getDescription())
                                        .build();

        return ResponseEntity.status(ErrorCodes.BAD_CREDENTIALS.getHttpStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMsg = error.getDefaultMessage();
            errors.put(fieldName, errorMsg);
        });

        var response = ExceptionResponse.builder()
                                        .errors(errors)        
                                        .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException ex) {
        var response = ExceptionResponse.builder()
                                        .error(ex.getMessage())
                                        .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(OperationNotPermittedException.class)
    public ResponseEntity<ExceptionResponse> handleException(OperationNotPermittedException ex) {
        var response = ExceptionResponse.builder()
                                        .error(ex.getMessage())
                                        .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex) {

        // log the exception
        ex.printStackTrace();
    
        var response = ExceptionResponse.builder()
                                        .errorDescrtiption("Internel error, please contact our team")
                                        .error(ex.getMessage())
                                        .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
