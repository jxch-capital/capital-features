package org.jxch.capital.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ResErrorEntity> handleConflict(@NotNull Exception ex, @NotNull WebRequest request) {
        return new ResponseEntity<>(ResErrorEntity.builder()
                .message(ex.getMessage())
                .path(request.getDescription(false))
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
