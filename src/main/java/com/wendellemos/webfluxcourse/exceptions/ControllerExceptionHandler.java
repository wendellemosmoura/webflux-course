package com.wendellemos.webfluxcourse.exceptions;


import com.wendellemos.webfluxcourse.service.exception.ObjectNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DuplicateKeyException.class)
    ResponseEntity<Mono<StandardError>> duplicateKeyException(DuplicateKeyException ex, ServerHttpRequest request) {
        return ResponseEntity.badRequest().body(Mono.just(
                StandardError.builder()
                        .timestamp(now())
                        .path(request.getPath().toString())
                        .status(BAD_REQUEST.value())
                        .error(BAD_REQUEST.getReasonPhrase())
                        .message(verifyDupKey(ex.getMessage()))
                        .build()));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Mono<ValidationError>> validationError(WebExchangeBindException ex, ServerHttpRequest request) {
        ValidationError error = new ValidationError(
                now(), request.getPath().toString(), BAD_REQUEST.value(), "Validation Error", "Attribute validation error"
        );

        for (FieldError x: ex.getBindingResult().getFieldErrors()) {
            error.addError(x.getField(), x.getDefaultMessage());
        }

        return ResponseEntity.status(BAD_REQUEST).body(Mono.just(error));
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    ResponseEntity<Mono<StandardError>> objectNotFoundException(ObjectNotFoundException ex, ServerHttpRequest request) {
        return ResponseEntity.status(NOT_FOUND).body(Mono.just(
                StandardError.builder()
                        .timestamp(now())
                        .path(request.getPath().toString())
                        .status(NOT_FOUND.value())
                        .error(NOT_FOUND.getReasonPhrase())
                        .message(ex.getMessage())
                        .build()));
    }

    private String verifyDupKey(String message) {
        if (message.contains("email dup key")) {
            return "Email already registered";
        } else {
            return "Dup key exception";
        }
    }
}
