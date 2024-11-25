package com.example.votingsystem.exception.custom;

import java.util.Map;
import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {
  private final Map<String, String> errors;

  public ValidationException(Map<String, String> errors) {
    this.errors = errors;
  }
}
