package com.example.votingsystem.exception;

import com.example.votingsystem.dto.response.generic.ErrorDetailsFieldResponse;
import com.example.votingsystem.dto.response.generic.ErrorField;
import com.example.votingsystem.dto.response.generic.ErrorResponse;
import com.example.votingsystem.exception.custom.EntityValidationException;
import com.example.votingsystem.exception.custom.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
    LOGGER.warn("Error: {}", ex.getMessage());
    return new ResponseEntity<>(
        new ErrorResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(EntityValidationException.class)
  public ResponseEntity<ErrorDetailsFieldResponse> handleCustomEntityValidationExceptions(
      EntityValidationException ex, WebRequest request) {
    List<ErrorField> details =
        ex.getErrors().entrySet().stream()
            .map(entry -> new ErrorField(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());

    LOGGER.warn("EntityValidationException: {}", details);
    
    return new ResponseEntity<>(
        new ErrorDetailsFieldResponse("Entity validation error", details), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFoundExceptions(
      EntityValidationException ex, WebRequest request) {

    LOGGER.warn("NotFoundException: {}", ex.getMessage());
    
    return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
  }
}
